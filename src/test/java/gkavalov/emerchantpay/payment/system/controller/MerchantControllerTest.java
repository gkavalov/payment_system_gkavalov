package gkavalov.emerchantpay.payment.system.controller;

import gkavalov.emerchantpay.payment.system.IntegrationTest;
import gkavalov.emerchantpay.payment.system.model.dto.CreateUpdateMerchantDto;
import gkavalov.emerchantpay.payment.system.model.dto.MerchantDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.AuthorizeTransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.MerchantStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static gkavalov.emerchantpay.payment.system.TestConstants.MOCK_MERCHANT_1;
import static gkavalov.emerchantpay.payment.system.TestConstants.MOCK_TRANSACTION_1;
import static gkavalov.emerchantpay.payment.system.controller.MerchantController.MERCHANTS_PATH;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.OK;

class MerchantControllerTest extends IntegrationTest {

    @Test
    void testMerchantCrudOperations() {
        final CreateUpdateMerchantDto merchantDto = merchantMapper.toCreateUpdateDto(MOCK_MERCHANT_1);

        // create a merchant
        final String merchantLocation = createMerchant(merchantDto);

        // check if merchant can be seen
        getAllMerchants(1);

        // check if merchant is created
        getMerchant(merchantLocation, merchantDto);

        // update the merchant
        merchantDto.setStatus(MerchantStatus.INACTIVE);
        given()
                .body(merchantDto)
                .contentType(MediaType.APPLICATION_JSON)
                .put(MERCHANTS_PATH + "/" + merchantLocation)
                .then()
                .status(OK);

        // check if merchant is updated
        getMerchant(merchantLocation, merchantDto);

        // delete the merchant
        deleteMerchant(merchantLocation);

        // get all the merchants
        getAllMerchants(0);
    }

    @Test
    @DirtiesContext
    void testTransactionForMerchant() {
        final CreateUpdateMerchantDto merchantDto = merchantMapper.toCreateUpdateDto(MOCK_MERCHANT_1);
        merchantDto.setTotalTransactionSum(new BigDecimal("0.0"));
        final AuthorizeTransactionDto transactionDto = (AuthorizeTransactionDto) transactionMapper.toDto(MOCK_TRANSACTION_1);

        final String merchantLocation = createMerchant(merchantDto);

        makeTransactionForMerchant(transactionDto, merchantLocation);

        final MerchantDto latestMerchant = getMerchant(merchantLocation, merchantDto);

        assertEquals(transactionDto.getCustomerAmount(), latestMerchant.getTransactions().toArray(new AuthorizeTransactionDto[]{})[0].getCustomerAmount());
    }

    @Test
    @DirtiesContext
    void testMerchantsImport() throws URISyntaxException {

        // check
        getAllMerchants(0);

        final URL initialMerchants = getClass().getClassLoader().getResource("merchants.csv");
        String importResponse = importMerchants(initialMerchants);

        // assert total merchants
        Pattern numberPattern = Pattern.compile("^(\\d+)(.*)$");
        Matcher matcher = numberPattern.matcher(importResponse);
        matcher.find();
        int initialImportSize = Integer.parseInt(matcher.group(1));
        getAllMerchants(initialImportSize);

        // add more merchants
        final URL moreMerchants = getClass().getClassLoader().getResource("many_merchants.csv");
        String moreMerchantsResponse = importMerchants(moreMerchants);

        // assert total merchants
        matcher = numberPattern.matcher(moreMerchantsResponse);
        matcher.find();
        int newlyImportedMerchants = Integer.parseInt(matcher.group(1));
        getAllMerchants(initialImportSize + newlyImportedMerchants);
     }
}
