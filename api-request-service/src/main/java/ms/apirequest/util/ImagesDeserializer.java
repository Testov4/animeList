package ms.apirequest.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ms.apirequest.model.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ImagesDeserializer extends JsonDeserializer<List<Image>> {
    @Override
    public List<Image> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        List<Image> images = new ArrayList<>();
        JsonNode node = jp.getCodec().readTree(jp);

        Iterator<Map.Entry<String, JsonNode>> imageTypeFields = node.fields();
        while (imageTypeFields.hasNext()) {
            Map.Entry<String, JsonNode> imageTypeField = imageTypeFields.next();
            String imageType = imageTypeField.getKey();
            JsonNode imageTypeNode = imageTypeField.getValue();

            String largeUrl = imageTypeNode.get("large_image_url").asText();
            String smallUrl = imageTypeNode.get("small_image_url").asText();
            String url = imageTypeNode.get("image_url").asText();

            Image imageDto = new Image(imageType, largeUrl, smallUrl, url);
            images.add(imageDto);
        }

        return images;
    }
}
