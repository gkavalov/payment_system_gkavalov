package gkavalov.emerchantpay.payment.system.exception;

public class InactiveMerchant extends Exception {

    public InactiveMerchant(final Long id) {
        super("Merchant %d is inactive and is not allowed to accept payments".formatted(id));
    }
}
