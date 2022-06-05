package lav.valentine.currencyrateandgif;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@EnableConfigurationProperties
@TestConfiguration
public class CurrencyRateAndGifApplicationTestsConfigurations {
    @Value("${wire-mock-server.port}")
    Integer serverPort;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer mockBooksService() {
        return new WireMockServer(serverPort);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}