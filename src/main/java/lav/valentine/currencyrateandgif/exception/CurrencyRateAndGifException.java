package lav.valentine.currencyrateandgif.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public abstract class CurrencyRateAndGifException extends RuntimeException {

    protected HttpStatus httpStatus;

    public CurrencyRateAndGifException(String message) {
        super(message);
    }
}