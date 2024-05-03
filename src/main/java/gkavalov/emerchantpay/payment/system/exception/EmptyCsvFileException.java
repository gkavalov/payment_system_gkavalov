package gkavalov.emerchantpay.payment.system.exception;

public class EmptyCsvFileException extends Exception {

    public EmptyCsvFileException() {
        super("Empty csv file provided");
    }
}
