package lav.valentine.currencyrateandgif.exception;

import lav.valentine.currencyrateandgif.controller.ExchangeRateGifController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class DefaultExceptionHandlerTest {

    @MockBean
    private ExchangeRateGifController exchangeRateGifController;

    private MockMvc mvc;

    private final String url = "/api/currencies/{currency}";
    private final String baseCurrency = "base-currency";
    private final String errorMessage = "error-message";

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new DefaultExceptionHandler(), exchangeRateGifController).build();
    }

    @Test
    void currencyRateAndGifExceptionHandlerConnection() throws Exception {
        when(exchangeRateGifController.getGifByCurrency(any()))
                .thenThrow(new ConnectionException(errorMessage));

        mvc.perform(MockMvcRequestBuilders.get(url, baseCurrency))
                .andExpect(content().string(errorMessage))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void currencyRateAndGifExceptionHandlerClient() throws Exception {
        when(exchangeRateGifController.getGifByCurrency(any()))
                .thenThrow(new ClientException(errorMessage));

        mvc.perform(MockMvcRequestBuilders.get(url, baseCurrency))
                .andExpect(content().string(errorMessage))
                .andExpect(status().isBadRequest());
    }
}