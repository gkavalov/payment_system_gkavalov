package gkavalov.emerchantpay.payment.system.exception;

public class InvalidTotalSumException extends Exception {

    public InvalidTotalSumException(final Long merchantId) {
        super("Merchant %d total sum does not tally up".formatted(merchantId));
    }
}
