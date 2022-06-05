package lav.valentine.currencyrateandgif.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import lav.valentine.currencyrateandgif.CurrencyRateAndGifApplicationTestsConfigurations;
import lav.valentine.currencyrateandgif.dto.GiphyDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = { CurrencyRateAndGifApplicationTestsConfigurations.class })
class GiphyClientTest {

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private GiphyClient giphyClient;

    @Autowired
    private ObjectMapper objectMapper;

    private final String apiKey = "api-key";
    private final String rating = "rating";
    private final String tag = "rich";
    private final String gifId = "gif-id";
    private final String testUrl = "/random";

    @BeforeEach
    void setUp() throws JsonProcessingException {
        wireMockServer.start();
        GiphyDto giphyDto = new GiphyDto(gifId);
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo(testUrl))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(giphyDto))));
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void getRandomGifGifDtoShouldBeReturned() {
        assertNotNull(giphyClient.getRandomGif(apiKey, tag, rating));
    }

    @Test
    void getRandomGifCorrectGifDtoShouldBeReturned() {
        GiphyDto result = giphyClient.getRandomGif(apiKey, tag, rating);
        assertEquals(gifId, result.getId());
    }
}