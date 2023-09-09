package ms.animedbservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class AnimeDto {
    @JsonProperty("mal_id")
    private Integer malId;
    private String url;
    private Map<String, ImageDto> images;
    private String title;
    @JsonProperty("title_english")
    private String titleEnglish;
    @JsonProperty("title_japanese")
    private String titleJapanese;
    @JsonProperty("title_synonyms")
    private List<String> titleSynonyms;
    private String type;
    private Integer episodes;
    private String airing;
    private String status;
    private String duration;
    private Integer year;
    private List<GenreDto> genres;
    private List<StudioDto> studios;
}
