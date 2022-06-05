package lav.valentine.currencyrateandgif.service;

import feign.FeignException;
import lav.valentine.currencyrateandgif.client.OpenExchangeRatesClient;
import lav.valentine.currencyrateandgif.dto.ExchangeRatesDto;
import lav.valentine.currencyrateandgif.exception.ClientException;
import lav.valentine.currencyrateandgif.exception.ConnectionException;
import lav.valentine.currencyrateandgif.service.impl.OpenExchangeRatesServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class OpenExchangeRatesServiceImplTest {

    @MockBean
    private OpenExchangeRatesClient client;

    @Autowired
    private OpenExchangeRatesServiceImpl openExchangeRatesService;

    private final String baseCurrency = "base-currency";
    private final String currency = "searched-currency";
    private final String nonExistedCurrency = "non-exist-currency";

    private final String date = "local-date";

    private final BigDecimal rate = new BigDecimal("63.49999");

    private final ExchangeRatesDto exchangeRatesDto =
            new ExchangeRatesDto(baseCurrency, Map.of(currency, rate));

    @Test
    void getCurrencyRateAnyReturned() {
        when(client.getHistoricalData(any(), any(), any()))
                .thenReturn(exchangeRatesDto);

        assertNotNull(openExchangeRatesService.getCurrencyRate(date, currency));
    }

    @Test
    void getCurrencyRateWhenCorrect() {
        when(client.getHistoricalData(any(), any(), any()))
                .thenReturn(exchangeRatesDto);

        assertEquals(rate, openExchangeRatesService.getCurrencyRate(date, currency));
    }

    @Test
    void getCurrencyRateWhenConnectionError() {
        when(client.getHistoricalData(any(), any(), any()))
                .thenThrow(FeignException.class);

        assertThrows(ConnectionException.class, () -> openExchangeRatesService.getCurrencyRate(date, currency));
    }

    @Test
    void getCurrencyRateWhenCurrencyError() {
        when(client.getHistoricalData(any(), any(), any()))
                .thenReturn(exchangeRatesDto);

        assertThrows(ClientException.class, () -> openExchangeRatesService.getCurrencyRate(date, nonExistedCurrency));
    }
}