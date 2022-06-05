package lav.valentine.currencyrateandgif.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lav.valentine.currencyrateandgif.dto.ResponseDto;
import lav.valentine.currencyrateandgif.service.ExchangeRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ExchangeRateGifController.class)
class ExchangeRateGifControllerTest {

    @MockBean
    private ExchangeRateService exchangeRateService;

    @Autowired
    private ExchangeRateGifController exchangeRateGifController;

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mvc;

    private final String baseCurrency = "base-currency";
    private final ResponseDto responseDto = ResponseDto.builder()
            .currency("RUB")
            .baseCurrency("USD")
            .gifUrl("gif-url")
            .build();

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(exchangeRateGifController).build();

        when(exchangeRateService.getData(any()))
                .thenReturn(responseDto);
    }

    @Test
    void getGifByCurrency() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/currencies/{currency}", baseCurrency))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(responseDto)));
    }

    @Test
    void getGifByCurrencyModel() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/currencies/{currency}/gif", baseCurrency))
                .andExpect(status().isOk()).andExpect(model().attribute("response", responseDto));
    }
}