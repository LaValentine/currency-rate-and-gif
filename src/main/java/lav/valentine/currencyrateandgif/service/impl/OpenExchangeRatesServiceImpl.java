package lav.valentine.currencyrateandgif.service.impl;

import feign.FeignException;
import lav.valentine.currencyrateandgif.client.OpenExchangeRatesClient;
import lav.valentine.currencyrateandgif.exception.ClientException;
import lav.valentine.currencyrateandgif.exception.ConnectionException;
import lav.valentine.currencyrateandgif.service.OpenExchangeRatesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class OpenExchangeRatesServiceImpl implements OpenExchangeRatesService {

    private final OpenExchangeRatesClient client;
    private final String baseCurrency;
    private final String appId;
    private final String invalidCurrencyException;
    private final String connectionException;

    public OpenExchangeRatesServiceImpl(OpenExchangeRatesClient client,
                                        @Value("${openexchangerates.base-currency}") String baseCurrency,
                                        @Value("${openexchangerates.app-id}") String appId,
                                        @Value("${exception.openexchangerates-connection}") String connectionException,
                                        @Value("${exception.openexchangerates}") String invalidCurrencyException) {
        this.client = client;
        this.baseCurrency = baseCurrency;
        this.appId = appId;
        this.invalidCurrencyException = invalidCurrencyException;
        this.connectionException = connectionException;
    }

    public BigDecimal getCurrencyRate(String date, String currency) {
        try {
            BigDecimal rate = client.getHistoricalData(date, appId, baseCurrency).getRates().get(currency);
            if (rate != null) {
                return rate;
            }
            else {
                log.warn(invalidCurrencyException + " (Entered currency code '" + currency + "')");
                throw new ClientException(invalidCurrencyException);
            }
        }
        catch (FeignException ex) {
            log.error(connectionException);
            throw new ConnectionException(connectionException);
        }
    }
}