package ms.animedbservice.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.animedbservice.dto.AnimeDto;
import ms.animedbservice.exception.WrongRequestFormatException;
import ms.animedbservice.model.AnimeSearchRequest;
import ms.animedbservice.service.DeserializerService;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeserializerServiceImpl implements DeserializerService {

    private final ObjectMapper mapper;

    @Override
    public AnimeSearchRequest deserializeAnimeSearchRequest(String json) {
        try {
            return mapper.readValue(json, AnimeSearchRequest.class);
        } catch (JsonProcessingException e) {
            log.error("Failed using mapper to read JSON: {}", e.getMessage());
            log.debug("JSON that caused the exception: {}", json);
            throw new WrongRequestFormatException("Failed to deserialize JSON: ", e);
        }
    }

    @Override
    public List<AnimeDto> deserializeAnimeList(String json) {
        try {
            return Arrays.asList(mapper.readValue(json, AnimeDto[].class));
        } catch (JsonProcessingException e) {
            log.error("Failed using mapper to read JSON: {}", e.getMessage());
            log.debug("JSON that caused the exception: {}", json);
            throw new WrongRequestFormatException("Failed to deserialize JSON: ", e);
        }
    }
}
