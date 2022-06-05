package lav.valentine.currencyrateandgif.exception;

import org.springframework.http.HttpStatus;

public class ConnectionException extends CurrencyRateAndGifException {

    public ConnectionException(String message) {
        super(message);
        super.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}