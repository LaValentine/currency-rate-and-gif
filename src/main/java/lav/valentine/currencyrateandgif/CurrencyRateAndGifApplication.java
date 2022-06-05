package lav.valentine.currencyrateandgif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"lav.valentine.currencyrateandgif.client"})
@SpringBootApplication
public class CurrencyRateAndGifApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyRateAndGifApplication.class, args);
    }

}
