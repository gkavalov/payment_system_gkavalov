package gkavalov.emerchantpay.payment.system.controller;

import gkavalov.emerchantpay.payment.system.IntegrationTest;
import gkavalov.emerchantpay.payment.system.model.dto.CreateUpdateMerchantDto;
import gkavalov.emerchantpay.payment.system.model.dto.MerchantDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.AuthorizeTransactionDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.ChargeTransactionDto;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static gkavalov.emerchantpay.payment.system.TestConstants.*;
import static gkavalov.emerchantpay.payment.system.controller.TransactionController.TRANSACTIONS_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.CREATED;

class TransactionsControllerTest extends IntegrationTest {

    @Test
    void testPayForTransaction() {
        // prepare necessary payloads
        final CreateUpdateMerchantDto merchantDto = merchantMapper.toCreateUpdateDto(MOCK_MERCHANT_1);
        merchantDto.setTotalTransactionSum(new BigDecimal("0.0"));
        final AuthorizeTransactionDto authoriseTransaction = (AuthorizeTransactionDto) transactionMapper.toDto(MOCK_TRANSACTION_1);

        // create records
        final String merchantLocation = createMerchant(merchantDto);
        final String transactionLocation = makeTransactionForMerchant(authoriseTransaction, merchantLocation);

        // check if transaction is created successfully in database
        AuthorizeTransactionDto retrievedTransaction = getTransaction(transactionLocation, AuthorizeTransactionDto.class);

        assertEquals(authoriseTransaction.getCustomerAmount(), retrievedTransaction.getCustomerAmount());
        assertEquals(authoriseTransaction.getReferenceId(), retrievedTransaction.getReferenceId());
        assertEquals(authoriseTransaction.getAmount(), retrievedTransaction.getAmount());
        assertEquals(authoriseTransaction.getStatus(), retrievedTransaction.getStatus());
        assertEquals(authoriseTransaction.getMerchant().getName(), retrievedTransaction.getMerchant().getName());

        // pay for transaction
        ChargeTransactionDto charge = transactionMapper.toDto(MOCK_TRANSACTION_2, ChargeTransactionDto.class);
        final String paidTransactionLocation = RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(charge)
                .post(transactionLocation + "/payment")
                .then()
                .status(CREATED)
                .extract()
                .response().getHeader("Location");

        ChargeTransactionDto chargedTransaction = getTransaction(TRANSACTIONS_PATH + "/" + paidTransactionLocation, ChargeTransactionDto.class);

        assertEquals(MOCK_TRANSACTION_2.getApprovedAmount(), chargedTransaction.getApprovedAmount());
        assertEquals(MOCK_TRANSACTION_2.getMerchant().getTotalTransactionSum(), chargedTransaction.getMerchant().getTotalTransactionSum());

        // assert if database records are updated accordingly
        MerchantDto merchant = getMerchant(merchantLocation, merchantDto);
        assertEquals(MOCK_TRANSACTION_2.getApprovedAmount().stripTrailingZeros(), merchant.getTotalTransactionSum().stripTrailingZeros());

        // cleanup
        deleteMerchant(merchantLocation);
    }
}
