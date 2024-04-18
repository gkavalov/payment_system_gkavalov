package gkavalov.emerchantpay.payment.system.model.dto.transaction;

import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefundTransactionDto extends TransactionDto {

    private BigDecimal reversedAmount;
}
