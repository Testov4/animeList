package ms.animedbservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GenreDto {
    @JsonProperty("mal_id")
    private Integer malId;
    private String type;
    private String name;
    private String url;

}
