package lav.valentine.currencyrateandgif.service.impl;

import feign.FeignException;
import lav.valentine.currencyrateandgif.client.GiphyClient;
import lav.valentine.currencyrateandgif.dto.GiphyDto;
import lav.valentine.currencyrateandgif.exception.ClientException;
import lav.valentine.currencyrateandgif.exception.ConnectionException;
import lav.valentine.currencyrateandgif.service.GiphyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GiphyServiceImpl implements GiphyService {

    private final GiphyClient giphyClient;
    private final String apiKey;
    private final String rating;
    private final String unknownException;
    private final String connectionException;


    public GiphyServiceImpl(GiphyClient giphyClient,
                            @Value("${giphy.api-key}") String apiKey,
                            @Value("${giphy.rating}") String rating,
                            @Value("${exception.giphy}") String unknownException,
                            @Value("${exception.giphy-connection}") String connectionException) {
        this.giphyClient = giphyClient;
        this.apiKey = apiKey;
        this.rating = rating;
        this.unknownException = unknownException;
        this.connectionException = connectionException;
    }

    @Override
    public String getGifUrlByTag(String tag) {
        try {
            GiphyDto giphyDto = giphyClient.getRandomGif(apiKey, tag, rating);
            if (giphyDto != null) {
                return "https://media2.giphy.com/media/" + giphyDto.getId() + "/giphy.gif";
            }
            else {
                log.warn(unknownException);
                throw new ClientException(unknownException);
            }
        }
        catch (FeignException ex) {
            log.error(connectionException);
            throw new ConnectionException(connectionException);
        }
    }
}