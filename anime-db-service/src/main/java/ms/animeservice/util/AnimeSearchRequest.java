package ms.animeservice.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class AnimeSearchRequest {

    @JsonProperty("q")
    private String title;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String type;
    private List<Integer> genres;

}
