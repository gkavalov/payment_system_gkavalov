package gkavalov.emerchantpay.payment.system.exception;

public class CorruptCsvFileException extends Exception {

    public CorruptCsvFileException(final Exception e) {
        super("Could not open merchants csv file", e);
    }
}
