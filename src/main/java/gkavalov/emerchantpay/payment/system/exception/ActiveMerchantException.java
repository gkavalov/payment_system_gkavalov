package gkavalov.emerchantpay.payment.system.exception;

public class ActiveMerchantException extends Exception {

    public ActiveMerchantException(final Long id, final int transactionsSize) {
        super("Merchant %d has %d transactions and cannot be deleted".formatted(id, transactionsSize));
    }
}
