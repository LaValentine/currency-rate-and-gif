package lav.valentine.currencyrateandgif.service;

import java.math.BigDecimal;

public interface OpenExchangeRatesService {

    BigDecimal getCurrencyRate(String date, String currency);
}