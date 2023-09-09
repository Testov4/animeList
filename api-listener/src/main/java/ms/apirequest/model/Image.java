package ms.apirequest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Image {

    @JsonProperty("large_image_url")
    private String largeUrl;
    @JsonProperty("small_image_url")
    private String smallUrl;
    @JsonProperty("image_url")
    private String url;

}
