package ms.apirequest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Genre {

    @JsonProperty("mal_id")
    private Integer malId;
    private String type;
    private String name;
    private String url;

}
