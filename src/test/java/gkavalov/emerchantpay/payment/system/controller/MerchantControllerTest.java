package gkavalov.emerchantpay.payment.system.controller;

import gkavalov.emerchantpay.payment.system.IntegrationTest;
import gkavalov.emerchantpay.payment.system.model.dto.CreateUpdateMerchantDto;
import gkavalov.emerchantpay.payment.system.model.dto.MerchantDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.AuthorizeTransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.MerchantStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

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
    void testTransactionForMerchant() {
        final CreateUpdateMerchantDto merchantDto = merchantMapper.toCreateUpdateDto(MOCK_MERCHANT_1);
        merchantDto.setTotalTransactionSum(new BigDecimal("0.0"));
        final AuthorizeTransactionDto transactionDto = (AuthorizeTransactionDto) transactionMapper.toDto(MOCK_TRANSACTION_1);

        final String merchantLocation = createMerchant(merchantDto);

        makeTransactionForMerchant(transactionDto, merchantLocation);

        final MerchantDto latestMerchant = getMerchant(merchantLocation, merchantDto);

        assertEquals(transactionDto.getCustomerAmount(), latestMerchant.getTransactions().toArray(new AuthorizeTransactionDto[]{})[0].getCustomerAmount());

        // clean up
        deleteMerchant(merchantLocation);
    }
}
