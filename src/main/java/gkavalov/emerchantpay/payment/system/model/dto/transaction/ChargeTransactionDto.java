package gkavalov.emerchantpay.payment.system.model.dto.transaction;

import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargeTransactionDto extends TransactionDto {

    private BigDecimal approvedAmount;


    /*public ChargeTransactionDto(final BigDecimal amount, final TransactionStatus status,
                                final String customerEmail, final String customerPhone, final String referenceId,
                                final TransactionDto belongsTo, final MerchantDto merchant, final BigDecimal approvedAmount) {
        super(amount, status, customerEmail, customerPhone, referenceId, belongsTo, merchant);
        this.approvedAmount = approvedAmount;
    }*/

}
