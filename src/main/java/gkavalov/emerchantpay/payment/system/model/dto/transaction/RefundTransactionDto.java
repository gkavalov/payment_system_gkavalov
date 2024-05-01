package gkavalov.emerchantpay.payment.system.model.dto.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.RefundTransaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefundTransactionDto extends TransactionDto {

    private BigDecimal reversedAmount;

    public RefundTransactionDto(final RefundTransaction refund) {
        super(refund.getAmount(), refund.getStatus(), refund.getCustomerEmail(), refund.getCustomerPhone(),
                refund.getReferenceId(), null, null);
        this.reversedAmount = refund.getReversedAmount();
    }

    public RefundTransactionDto(final JsonNode node) {
        super(node);
        this.reversedAmount = node.get("reversedAmount").decimalValue();
    }
}
