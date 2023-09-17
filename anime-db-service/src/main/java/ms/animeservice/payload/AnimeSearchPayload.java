package ms.animeservice.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class AnimeSearchPayload {

    @JsonProperty("q")
    private String title;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String type;
    private List<Integer> genres;

}
