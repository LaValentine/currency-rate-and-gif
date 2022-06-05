package lav.valentine.currencyrateandgif.client;

import lav.valentine.currencyrateandgif.dto.ExchangeRatesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "open-exchange-rates-client", url = "${openexchangerates.url}")
public interface OpenExchangeRatesClient {

    @GetMapping("/historical/{date}.json")
    ExchangeRatesDto getHistoricalData(
            @PathVariable String date,
            @RequestParam("app_id") String appId,
            @RequestParam("base") String base
    );
}