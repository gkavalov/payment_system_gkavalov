package gkavalov.emerchantpay.payment.system.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleMissingData(final EntityNotFoundException ex) {
        log.warn(ex.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({InactiveMerchantException.class, EmptyCsvFileException.class})
    public ResponseEntity<String> handleInactiveMerchant(final Exception ex) {
        log.warn(ex.getMessage());
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralError(final Exception ex) {
        log.error(ex.getMessage());
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}
