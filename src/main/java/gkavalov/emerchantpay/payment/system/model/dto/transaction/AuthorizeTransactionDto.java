package gkavalov.emerchantpay.payment.system.model.dto.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.AuthorizeTransaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorizeTransactionDto extends TransactionDto {

    private BigDecimal customerAmount;

    public AuthorizeTransactionDto(final AuthorizeTransaction auth) {
        super(auth.getAmount(), auth.getStatus(), auth.getCustomerEmail(), auth.getCustomerPhone(),
                auth.getReferenceId(), null, null);
        this.customerAmount = auth.getCustomerAmount();
    }
}
