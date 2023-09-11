package ms.animeservice.util.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class CompressedAnimeDto {
    @JsonProperty("mal_id")
    private Integer malId;
    private String url;
    private List<ImageDto> images;
    private String title;
    @JsonProperty("title_english")
    private String titleEnglish;
    @JsonProperty("title_japanese")
    private String titleJapanese;
    private String type;
    private Integer episodes;
    private String airing;
    private String status;
    private String duration;
    private Integer year;
}
