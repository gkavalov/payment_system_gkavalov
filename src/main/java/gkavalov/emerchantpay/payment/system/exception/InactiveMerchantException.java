package gkavalov.emerchantpay.payment.system.exception;

public class InactiveMerchantException extends Exception {

    public InactiveMerchantException(final Long id) {
        super("Merchant %d is inactive and is not allowed to accept transactions".formatted(id));
    }
}
