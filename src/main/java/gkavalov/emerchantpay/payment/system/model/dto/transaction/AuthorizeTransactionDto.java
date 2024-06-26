package gkavalov.emerchantpay.payment.system.model.dto.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.TransactionStatus;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.AuthorizeTransaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorizeTransactionDto extends TransactionDto {

    @Positive
    @NotNull
    private BigDecimal customerAmount;

    public AuthorizeTransactionDto(final ZonedDateTime timestamp, final TransactionStatus status, final String customerEmail,
                                   final String customerPhone, final String referenceId, final BigDecimal customerAmount) {
        super(timestamp, status, customerEmail, customerPhone, referenceId, null, null);
        this.customerAmount = customerAmount;
    }

    public AuthorizeTransactionDto(final JsonNode node) {
        super(node);
        this.customerAmount = node.get("customerAmount").decimalValue();
    }

    public AuthorizeTransactionDto(final AuthorizeTransaction auth) {
        super(auth.getTimestamp(), auth.getStatus(), auth.getCustomerEmail(), auth.getCustomerPhone(),
                auth.getReferenceId(), null, null);
        this.customerAmount = auth.getCustomerAmount();
    }
}
