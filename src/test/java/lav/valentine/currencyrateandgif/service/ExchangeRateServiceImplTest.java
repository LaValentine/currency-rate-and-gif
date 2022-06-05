package lav.valentine.currencyrateandgif.service;

import lav.valentine.currencyrateandgif.dto.ResponseDto;
import lav.valentine.currencyrateandgif.service.impl.ExchangeRateServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class ExchangeRateServiceImplTest {

    @MockBean
    private OpenExchangeRatesService openExchangeRatesService;

    @MockBean
    private GiphyService giphyService;

    @Autowired
    private ExchangeRateServiceImpl exchangeRateService;

    private final String baseCurrency = "base-currency";
    private final String currency = "searched-currency";

    private final String gifUrl = "gif-url";

    private final String todayDate = getDataFormat(LocalDate.now());
    private final String yesterdayDate = getDataFormat(LocalDate.now().minus(Period.ofDays(1)));

    private final BigDecimal firstNumber = new BigDecimal("63.49999");
    private final BigDecimal higherThenFirstNumber = new BigDecimal("65.49999");

    private String getDataFormat(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Test
    void getDataWhenCurrencyEqualsBase() {
        assertEquals(baseCurrency, exchangeRateService.getData(baseCurrency).getCurrency());
    }

    @Test
    void getDataWhenRich() {
        when(openExchangeRatesService.getCurrencyRate(eq(todayDate), any()))
                .thenReturn(higherThenFirstNumber);
        when(openExchangeRatesService.getCurrencyRate(eq(yesterdayDate), any()))
                .thenReturn(firstNumber);
        when(giphyService.getGifUrlByTag(any())).thenReturn(gifUrl);

        ResponseDto result = exchangeRateService.getData(currency);

        assertEquals(baseCurrency, result.getBaseCurrency());
        assertEquals(currency, result.getCurrency());
        assertEquals(higherThenFirstNumber, result.getTodayRate());
        assertEquals(firstNumber, result.getYesterdayRate());
        assertEquals(gifUrl, result.getGifUrl());
    }

    @Test
    void getDataWhenBroke() {
        when(openExchangeRatesService.getCurrencyRate(eq(todayDate), any()))
                .thenReturn(firstNumber);
        when(openExchangeRatesService.getCurrencyRate(eq(yesterdayDate), any()))
                .thenReturn(higherThenFirstNumber);
        when(giphyService.getGifUrlByTag(any()))
                .thenReturn(gifUrl);

        ResponseDto result = exchangeRateService.getData(currency);

        assertEquals(baseCurrency, result.getBaseCurrency());
        assertEquals(currency, result.getCurrency());
        assertEquals(firstNumber, result.getTodayRate());
        assertEquals(higherThenFirstNumber, result.getYesterdayRate());
        assertEquals(gifUrl, result.getGifUrl());
    }

    @Test
    void getDataWhenCurrencyRateEquals() {
        when(openExchangeRatesService.getCurrencyRate(any(), any()))
                .thenReturn(firstNumber);

        ResponseDto result = exchangeRateService.getData(currency);

        assertEquals(baseCurrency, result.getBaseCurrency());
        assertEquals(currency, result.getCurrency());
        assertEquals(firstNumber, result.getTodayRate());
        assertEquals(firstNumber, result.getYesterdayRate());
    }
}