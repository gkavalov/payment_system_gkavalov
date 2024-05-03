package gkavalov.emerchantpay.payment.system.service;

import gkavalov.emerchantpay.payment.system.IntegrationTest;
import gkavalov.emerchantpay.payment.system.model.dto.CreateUpdateMerchantDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.AuthorizeTransactionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static gkavalov.emerchantpay.payment.system.TestConstants.MOCK_MERCHANT_1;
import static gkavalov.emerchantpay.payment.system.TestConstants.MOCK_TRANSACTION_1;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.awaitility.Awaitility.await;
import static org.springframework.http.HttpStatus.NOT_FOUND;

class TransactionCleanerTest extends IntegrationTest {

    @Value("${payment.system.transactions.cleaner.timeLimitInMins}")
    private int getTimeLimitInMins;

    @Value("${payment.system.transactions.cleaner.frequencyInSeconds}")
    private int frequencyInSeconds;

    @Test
    @DirtiesContext
    void testTransactionCleaning() {
        final CreateUpdateMerchantDto merchantDto = merchantMapper.toCreateUpdateDto(MOCK_MERCHANT_1);
        merchantDto.setTotalTransactionSum(new BigDecimal("0.0"));
        final AuthorizeTransactionDto transactionDto = (AuthorizeTransactionDto) transactionMapper.toDto(MOCK_TRANSACTION_1);

        final String merchantLocation = createMerchant(merchantDto);

        String transactionLocation = makeTransactionForMerchant(transactionDto, merchantLocation);

        getTransaction(transactionLocation, AuthorizeTransactionDto.class);

        await().pollDelay(frequencyInSeconds, TimeUnit.SECONDS)
                .atMost(getTimeLimitInMins * 60L + 10, TimeUnit.SECONDS)
                .pollInterval(getTimeLimitInMins * 60L, TimeUnit.SECONDS)
                .until(() -> transactionIsNotFoundAnymore(transactionLocation));
    }

    private static boolean transactionIsNotFoundAnymore(String transactionLocation) {
        return given()
                .get(transactionLocation)
                .then()
                .extract().statusCode() == NOT_FOUND.value();
    }
}
