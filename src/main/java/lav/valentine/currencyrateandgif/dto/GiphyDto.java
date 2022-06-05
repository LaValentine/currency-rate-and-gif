package lav.valentine.currencyrateandgif.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class GiphyDto {

        private Data data;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Data {
            private String id;
        }

        @JsonIgnore
        public GiphyDto(String id) {
                this.data = new Data(id);
        }

        @JsonIgnore
        public String getId() {
            return data.getId();
        }
}