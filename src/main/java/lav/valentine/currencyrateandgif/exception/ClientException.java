package lav.valentine.currencyrateandgif.exception;

import org.springframework.http.HttpStatus;

public class ClientException extends CurrencyRateAndGifException {

    public ClientException(String message) {
        super(message);
        super.httpStatus = HttpStatus.BAD_REQUEST;
    }
}