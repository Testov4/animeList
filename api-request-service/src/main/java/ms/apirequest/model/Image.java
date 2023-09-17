package ms.apirequest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Image {

    private String imageType;

    @JsonProperty("large_image_url")
    private String largeUrl;
    @JsonProperty("small_image_url")
    private String smallUrl;
    @JsonProperty("image_url")
    private String url;

}
