package gkavalov.emerchantpay.payment.system.model.dto;

import com.fasterxml.jackson.databind.JsonNode;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.AuthorizeTransactionDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.ChargeTransactionDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.RefundTransactionDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.ReversalTransactionDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionDtoFactory {

    public static TransactionDto makeTransaction(final JsonNode node) {
        if (node == null) {
            return null;
        }

        if (node.has("customerAmount")) {
            return new AuthorizeTransactionDto(node);
        } else if (node.has("approvedAmount")) {
            return new ChargeTransactionDto(node);
        } else if (node.has("reversedAmount")) {
            return new RefundTransactionDto(node);
        } else {
            return new ReversalTransactionDto(node);
        }
    }

}
