package ms.apirequest.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
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
    @JsonSetter(nulls = Nulls.SKIP)
    private String type = "unknown";
    @JsonSetter(nulls = Nulls.SKIP)
    private Integer episodes = 0;
    @JsonSetter(nulls = Nulls.SKIP)
    private String airing = "unknown";
    @JsonSetter(nulls = Nulls.SKIP)
    private String status = "unknown";
    @JsonSetter(nulls = Nulls.SKIP)
    private String duration = "unknown";
    @JsonSetter(nulls = Nulls.SKIP)
    private Integer year = 0;
    private List<Genre> genres;
    private List<Studio> studios;

}
