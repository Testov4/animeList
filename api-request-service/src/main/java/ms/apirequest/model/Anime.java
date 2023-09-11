package ms.apirequest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ms.apirequest.util.ImagesDeserializer;
import ms.apirequest.util.SynonymsDeserializer;
import java.util.List;

@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Anime {

    @JsonProperty("mal_id")
    private Integer malId;
    private String url;
    @JsonDeserialize(using = ImagesDeserializer.class)
    private List<Image> images;
    private String title;
    @JsonProperty("title_english")
    private String titleEnglish;
    @JsonProperty("title_japanese")
    private String titleJapanese;
    @JsonProperty("title_synonyms")
    @JsonDeserialize(using = SynonymsDeserializer.class)
    private List<TitleSynonym> titleSynonyms;
    private String type;
    private Integer episodes;
    private String airing;
    private String status;
    private String duration;
    private Integer year;
    private List<Genre> genres;
    private List<Studio> studios;

}
