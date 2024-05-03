package gkavalov.emerchantpay.payment.system.model.dto.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.ChargeTransaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChargeTransactionDto extends TransactionDto {

    private BigDecimal approvedAmount;

    public ChargeTransactionDto(final ChargeTransaction charge) {
        super(charge.getAmount(), charge.getTimestamp(), charge.getStatus(), charge.getCustomerEmail(), charge.getCustomerPhone(),
                charge.getReferenceId(), null, null);
        this.approvedAmount = charge.getApprovedAmount();
    }

    public ChargeTransactionDto(final JsonNode node) {
        super(node);
        this.approvedAmount = node.get("approvedAmount").decimalValue();
    }
}
