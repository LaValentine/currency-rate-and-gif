package lav.valentine.currencyrateandgif.service.impl;

import lav.valentine.currencyrateandgif.dto.ResponseDto;
import lav.valentine.currencyrateandgif.service.ExchangeRateService;
import lav.valentine.currencyrateandgif.service.GiphyService;
import lav.valentine.currencyrateandgif.service.OpenExchangeRatesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final OpenExchangeRatesService openExchangeRatesService;
    private final GiphyService giphyService;
    private final String baseCurrency;
    private final String tagRich;
    private final String tagBroke;

    public ExchangeRateServiceImpl(OpenExchangeRatesService openExchangeRatesService,
                                   GiphyService giphyService,
                                   @Value("${openexchangerates.base-currency}") String baseCurrency,
                                   @Value("${giphy.tag-rich}") String tagRich,
                                   @Value("${giphy.tag-broke}") String tagBroke) {
        this.openExchangeRatesService = openExchangeRatesService;
        this.baseCurrency = baseCurrency;
        this.giphyService = giphyService;
        this.tagRich = tagRich;
        this.tagBroke = tagBroke;
    }

    private String getDataFormat(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public ResponseDto getData(String currency) {
        if (currency.equals(baseCurrency)) {
            return ResponseDto.builder()
                    .currency(currency)
                    .description("The currency code equals with base currency code").build();
        }

        BigDecimal yesterdayRate = openExchangeRatesService.getCurrencyRate(getDataFormat(LocalDate.now().minus(Period.ofDays(1))), currency);
        BigDecimal todayRate = openExchangeRatesService.getCurrencyRate(getDataFormat(LocalDate.now()), currency);
        String gifUrl;
        switch (todayRate.compareTo(yesterdayRate)) {
            case 1 : gifUrl = giphyService.getGifUrlByTag(tagRich); break;
            case -1 : gifUrl = giphyService.getGifUrlByTag(tagBroke); break;
            default : return ResponseDto.builder()
                    .currency(currency)
                    .baseCurrency(baseCurrency)
                    .yesterdayRate(yesterdayRate)
                    .todayRate(todayRate)
                    .description("The exchange rate has not changed in a day").build();
        }

        return ResponseDto.builder()
                .currency(currency)
                .baseCurrency(baseCurrency)
                .yesterdayRate(yesterdayRate)
                .todayRate(todayRate)
                .gifUrl(gifUrl).build();
    }
}