package gkavalov.emerchantpay.payment.system.model.dto.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.ChargeTransaction;
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
public class ChargeTransactionDto extends TransactionDto {

    private BigDecimal approvedAmount;

    public ChargeTransactionDto(final ChargeTransaction charge) {
        super(charge.getAmount(), charge.getStatus(), charge.getCustomerEmail(), charge.getCustomerPhone(),
                charge.getReferenceId(), null, null);
        this.approvedAmount = charge.getApprovedAmount();
    }
}
