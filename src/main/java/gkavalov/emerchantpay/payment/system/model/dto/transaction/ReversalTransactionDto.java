package gkavalov.emerchantpay.payment.system.model.dto.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.ReversalTransaction;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReversalTransactionDto extends TransactionDto {

    public ReversalTransactionDto(final ReversalTransaction reversal) {
        super(reversal.getTimestamp(), reversal.getStatus(), reversal.getCustomerEmail(), reversal.getCustomerPhone(),
                reversal.getReferenceId(), null, null);
    }

    public ReversalTransactionDto(final JsonNode node) {
        super(node);
    }
}
