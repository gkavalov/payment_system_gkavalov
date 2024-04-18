package gkavalov.emerchantpay.payment.system.model.dto.transaction;

import gkavalov.emerchantpay.payment.system.model.dto.MerchantDto;
import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizeTransactionDto extends TransactionDto {

    private BigDecimal customerAmount;

    public AuthorizeTransactionDto(final Double amount, final TransactionStatus status,
                                   final String customerEmail, final String customerPhone, final String referenceId,
                                   final TransactionDto belongsTo, final Double customerAmount) {
        super(new BigDecimal(amount), status, customerEmail, customerPhone, referenceId, belongsTo, new MerchantDto());
        this.customerAmount = new BigDecimal(customerAmount);
    }
}
