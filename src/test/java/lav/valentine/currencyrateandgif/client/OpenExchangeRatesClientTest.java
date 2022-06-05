package lav.valentine.currencyrateandgif.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import lav.valentine.currencyrateandgif.CurrencyRateAndGifApplicationTestsConfigurations;
import lav.valentine.currencyrateandgif.dto.ExchangeRatesDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = { CurrencyRateAndGifApplicationTestsConfigurations.class })
class OpenExchangeRatesClientTest {

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private OpenExchangeRatesClient openExchangeRatesClient;

    @Autowired
    private ObjectMapper objectMapper;

    private final String appId = "app-id";
    private final String date = "date";
    private final String base = "USD";

    private final String testUrl = "/historical/date.json";

    private final ExchangeRatesDto exchangeRatesDto = new ExchangeRatesDto(base,
            Map.of("RUB", new BigDecimal("63.49999"),
                    "EUR", new BigDecimal("0.98"),
                    "USD", new BigDecimal("1")));

    @BeforeEach
    void setUp() throws JsonProcessingException {
        wireMockServer.start();
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo(testUrl))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(exchangeRatesDto))));
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void getHistoricalDataExchangeRatesDtoShouldBeReturned() {
        assertNotNull(openExchangeRatesClient.getHistoricalData(date, appId, base));
    }

    @Test
    void getHistoricalDataCorrectExchangeRatesDtoShouldBeReturned() {
        ExchangeRatesDto result = openExchangeRatesClient.getHistoricalData(date, appId, base);

        Map<String, BigDecimal> received = result.getRates();
        Map<String, BigDecimal> provide = exchangeRatesDto.getRates();

        assertEquals(base, result.getBase());
        assertEquals(received.size(), provide.size());
        assertTrue(received.keySet().containsAll(provide.keySet()));

        provide.keySet().forEach((key) -> assertEquals(provide.get(key), received.get(key)));
    }
}