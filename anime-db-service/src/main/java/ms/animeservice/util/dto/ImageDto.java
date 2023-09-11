package ms.animeservice.util.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {

    private String imageType;

    @JsonProperty("large_image_url")
    private String largeUrl;
    @JsonProperty("small_image_url")
    private String smallUrl;
    @JsonProperty("image_url")
    private String url;

}
