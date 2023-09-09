package ms.apirequest.service.implementation;

import ms.apirequest.exception.WrongRequestFormatException;
import ms.apirequest.service.RequestDeserializerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestDeserializerServiceImpl implements RequestDeserializerService {

    private final ObjectMapper mapper;

    @Override
    public Map<String, Object> deserializeAnimeSearchRequest(String json) {
        try {
            return mapper.readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            log.error("Failed using mapper to read JSON: {}", e.getMessage());
            log.debug("JSON that caused the exception: {}", json);
            throw new WrongRequestFormatException("Failed to deserialize JSON");
        }
    }

}
