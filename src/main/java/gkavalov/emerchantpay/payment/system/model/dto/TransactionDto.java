package gkavalov.emerchantpay.payment.system.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gkavalov.emerchantpay.payment.system.model.entity.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = TransactionDtoDeserialiser.class)
public abstract class TransactionDto {

    @Positive
    @NotNull
    private BigDecimal amount;

    private TransactionStatus status;

    @Email
    private String customerEmail;

    private String customerPhone;

    private String referenceId;

    @JsonDeserialize(using = TransactionDtoDeserialiser.class)
    private TransactionDto belongsTo;

    private MerchantDto merchant;

    protected TransactionDto(final JsonNode node) {
        this.amount = node.has("amount") ? node.get("amount").decimalValue() : new BigDecimal("0.0");
        this.status = node.has("status") ? TransactionStatus.valueOf(node.get("status").asText()) : null;
        this.customerEmail = node.has("customerEmail") ? node.get("customerEmail").asText() : null;
        this.customerPhone = node.has("customerPhone") ? node.get("customerPhone").asText() : null;
        this.referenceId = node.has("referenceId") ? node.get("referenceId").asText() : null;
        this.belongsTo = TransactionDtoFactory.makeTransaction(node.get("belongsTo"));
        this.merchant = node.has("merchant") ? new MerchantDto(node.get("merchant")) : null;
    }
}
