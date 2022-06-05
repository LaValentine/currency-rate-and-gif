package lav.valentine.currencyrateandgif.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lav.valentine.currencyrateandgif.dto.ResponseDto;
import lav.valentine.currencyrateandgif.service.ExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequestMapping("/api")
public class ExchangeRateGifController {

    private final ExchangeRateService exchangeRateService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ExchangeRateGifController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping("/currencies/{currency}")
    public ResponseEntity<ResponseDto> getGifByCurrency(@PathVariable("currency") String currency) throws JsonProcessingException {
        ResponseDto response = exchangeRateService.getData(currency);

        log.debug("Sending response: " + objectMapper.writeValueAsString(response));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/currencies/{currency}/gif")
    public ModelAndView getGifByCurrencyModel(@PathVariable("currency") String currency) throws JsonProcessingException {
        ResponseDto response = exchangeRateService.getData(currency);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        modelAndView.addObject("response", response);

        log.debug("Sending response: " + objectMapper.writeValueAsString(response));

        return modelAndView;
    }
}