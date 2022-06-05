package lav.valentine.currencyrateandgif.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(value = CurrencyRateAndGifException.class)
    public ResponseEntity<String> currencyRateAndGifExceptionHandler(CurrencyRateAndGifException ex) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ex.getMessage());
    }
}