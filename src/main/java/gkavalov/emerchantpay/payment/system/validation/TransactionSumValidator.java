package gkavalov.emerchantpay.payment.system.validation;

import gkavalov.emerchantpay.payment.system.exception.InvalidTotalSumException;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import gkavalov.emerchantpay.payment.system.model.entity.TransactionStatus;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.ChargeTransaction;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.RefundTransaction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionSumValidator {

    public static void validateTotalTransactionSum(final Merchant merchant) throws InvalidTotalSumException {
        BigDecimal expectedTotalTransactionSum = merchant.getTotalTransactionSum();
        BigDecimal actualChargedSum = merchant.getTransactions()
                .stream()
                .filter(ChargeTransaction.class::isInstance)
                .filter(t -> t.getStatus() != TransactionStatus.ERROR)
                .map(t -> ((ChargeTransaction) t).getApprovedAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal actualRefundedSum = merchant.getTransactions()
                .stream()
                .filter(RefundTransaction.class::isInstance)
                .filter(t -> t.getStatus() != TransactionStatus.ERROR)
                .map(t -> ((RefundTransaction) t).getReversedAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (!expectedTotalTransactionSum.stripTrailingZeros().equals(actualChargedSum.subtract(actualRefundedSum).stripTrailingZeros())) {
            throw new InvalidTotalSumException(merchant.getId());
        }
    }
}
