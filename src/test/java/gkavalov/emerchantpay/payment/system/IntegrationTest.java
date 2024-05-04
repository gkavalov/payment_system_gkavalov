package gkavalov.emerchantpay.payment.system;

import gkavalov.emerchantpay.payment.system.mapper.MerchantMapper;
import gkavalov.emerchantpay.payment.system.mapper.TransactionMapper;
import gkavalov.emerchantpay.payment.system.model.dto.CreateUpdateMerchantDto;
import gkavalov.emerchantpay.payment.system.model.dto.MerchantDto;
import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import static gkavalov.emerchantpay.payment.system.controller.MerchantController.MERCHANTS_PATH;
import static io.restassured.http.ContentType.TEXT;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class IntegrationTest {

    @Autowired
    protected MerchantMapper merchantMapper;

    @Autowired
    protected TransactionMapper transactionMapper;

    @Autowired
    protected MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    protected String createMerchant(CreateUpdateMerchantDto merchantDto) {
        MockMvcResponse createMerchantResponse = given()
                .body(merchantDto)
                .contentType(MediaType.APPLICATION_JSON)
                .post(MERCHANTS_PATH)
                .then()
                .status(CREATED)
                .extract()
                .response();
        return createMerchantResponse.getHeader("Location");
    }

    protected void getAllMerchants(int sizeAssert) {
        given()
                .get(MERCHANTS_PATH)
                .then()
                .status(OK)
                .body("$.size()", Matchers.equalTo(sizeAssert));
    }

    protected MerchantDto getMerchant(String merchantLocation, CreateUpdateMerchantDto merchantDto) {
        return given()
                .get(MERCHANTS_PATH + "/" + merchantLocation)
                .then()
                .status(OK)
                .body("name", Matchers.equalTo(merchantDto.getName()))
                .body("status", Matchers.equalTo(merchantDto.getStatus().toString()))
                .extract().response().as(MerchantDto.class);
    }

    protected void deleteMerchant(String merchantLocation) {
        given()
                .delete(MERCHANTS_PATH + "/" + merchantLocation)
                .then()
                .status(OK)
                .contentType(TEXT);
    }

    protected String makeTransactionForMerchant(TransactionDto transactionDto, String merchantLocation) {
        MockMvcResponse makeTransactionResponse = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(transactionDto)
                .post(MERCHANTS_PATH + "/" + merchantLocation + "/transactions")
                .then()
                .status(CREATED)
                .extract()
                .response();
        return makeTransactionResponse.getHeader("Location");
    }

    protected static <T> T getTransaction(String transactionLocation, Class<? extends T> transactionType) {
        return given()
                .get(transactionLocation)
                .then()
                .status(OK)
                .extract().response().as(transactionType);
    }

    protected String importMerchants(URL resource) throws URISyntaxException {
        return given()
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .multiPart(new File(resource.toURI()))
                .post(MERCHANTS_PATH)
                .then()
                .status(OK)
                .extract().response().asString();
    }

    protected String referTransaction(TransactionDto charge, String transactionLocation) {
        return RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(charge)
                .post(transactionLocation)
                .then()
                .status(CREATED)
                .extract()
                .response().getHeader("Location");
    }
}
