package lav.valentine.currencyrateandgif.service;

import feign.FeignException;
import lav.valentine.currencyrateandgif.client.GiphyClient;
import lav.valentine.currencyrateandgif.dto.GiphyDto;
import lav.valentine.currencyrateandgif.exception.ClientException;
import lav.valentine.currencyrateandgif.exception.ConnectionException;
import lav.valentine.currencyrateandgif.service.impl.GiphyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class GiphyServiceImplTest {

    @MockBean
    private GiphyClient giphyClient;

    @Autowired
    private GiphyServiceImpl giphyService;

    private final String tag = "tag-correct";

    private final String gifId = "gif-id";
    private final String correctGifUrl = "https://media2.giphy.com/media/" + gifId + "/giphy.gif";

    @Test
    void getGifUrlByTagAnyReturned() {
        when(giphyClient.getRandomGif(any(), any(), any()))
                .thenReturn(new GiphyDto(gifId));

        assertNotNull(giphyService.getGifUrlByTag(tag));
    }

    @Test
    void getGifUrlByTagWhenCorrect() {
        when(giphyClient.getRandomGif(any(), eq(tag), any()))
                .thenReturn(new GiphyDto(gifId));

        assertEquals(giphyService.getGifUrlByTag(tag), correctGifUrl);
    }

    @Test
    void getGifUrlByTagWhenConnectionError() {
        when(giphyClient.getRandomGif(any(), any(), any()))
                .thenThrow(FeignException.class);

        assertThrows(ConnectionException.class, () -> giphyService.getGifUrlByTag(tag));
    }

    @Test
    void getGifUrlByTagWhenUnknownError() {
        when(giphyClient.getRandomGif(any(), any(), any()))
                .thenReturn(null);

        assertThrows(ClientException.class, () -> giphyService.getGifUrlByTag(tag));
    }
}