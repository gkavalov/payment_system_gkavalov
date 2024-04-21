package gkavalov.emerchantpay.payment.system.exception;

public class NonPayableTransactionException extends Exception {

    public NonPayableTransactionException(final String transactionType, final String uuid) {
        super("%s with uuid %s cannot be paid".formatted(transactionType, uuid));
    }
}
