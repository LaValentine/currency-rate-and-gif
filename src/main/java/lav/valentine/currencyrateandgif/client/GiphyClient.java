package lav.valentine.currencyrateandgif.client;

import lav.valentine.currencyrateandgif.dto.GiphyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "giphy-client", url = "${giphy.url}")
public interface GiphyClient {

    @GetMapping("/random")
    GiphyDto getRandomGif(
            @RequestParam("api_key") String apiKey,
            @RequestParam("tag") String tag,
            @RequestParam("rating") String rating
    );
}