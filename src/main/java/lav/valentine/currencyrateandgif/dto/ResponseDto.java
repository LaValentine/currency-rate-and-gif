package lav.valentine.currencyrateandgif.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {

    private String currency;
    private String baseCurrency;
    private BigDecimal yesterdayRate;
    private BigDecimal todayRate;
    private String gifUrl;
    private String description;
}