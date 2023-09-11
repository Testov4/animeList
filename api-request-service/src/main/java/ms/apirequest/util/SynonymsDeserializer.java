package ms.apirequest.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ms.apirequest.model.TitleSynonym;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SynonymsDeserializer extends JsonDeserializer<List<TitleSynonym>> {
    @Override
    public List<TitleSynonym> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        List<TitleSynonym> titleSynonyms = new ArrayList<>();
        JsonNode node = jp.getCodec().readTree(jp);

        if (node.isArray()) {
            for (JsonNode synonymNode : node) {
                if (synonymNode.isTextual()) {
                    String synonym = synonymNode.asText();
                    titleSynonyms.add(new TitleSynonym(synonym));
                }
            }
        }

        return titleSynonyms;
    }
}
