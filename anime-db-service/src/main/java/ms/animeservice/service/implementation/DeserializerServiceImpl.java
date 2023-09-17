package ms.animeservice.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.animeservice.payload.AnimeListPayload;
import ms.animeservice.payload.SingleAnimePayload;
import ms.animeservice.model.dto.AnimeDto;
import ms.animeservice.exception.WrongRequestFormatException;
import ms.animeservice.payload.AnimeSearchPayload;
import ms.animeservice.service.DeserializerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeserializerServiceImpl implements DeserializerService {

    private final ObjectMapper mapper;

    @Override
    public AnimeSearchPayload deserializeAnimeSearchRequest(String json) {
        try {
            return mapper.readValue(json, AnimeSearchPayload.class);
        } catch (JsonProcessingException e) {
            log.error("Failed using mapper to read JSON: {}", e.getMessage());
            log.debug("JSON that caused the exception: {}", json);
            throw new WrongRequestFormatException("Failed to deserialize JSON: ", e);
        }
    }

    @Override
    public List<AnimeDto> deserializeAnimeList(String json) {
        try {
            return mapper.readValue(json, new TypeReference<List<AnimeDto>>() {});
        } catch (JsonProcessingException e) {
            log.error("Failed using mapper to read JSON: {}", e.getMessage());
            log.debug("JSON that caused the exception: {}", json);
            throw new WrongRequestFormatException("Failed to deserialize JSON: ", e);
        }
    }

    @Override
    public SingleAnimePayload deserializeAnimeId(String json) {
        try {
            return mapper.readValue(json, SingleAnimePayload.class);
        } catch (JsonProcessingException e) {
            log.error("Failed using mapper to read JSON: {}", e.getMessage());
            log.debug("JSON that caused the exception: {}", json);
            throw new WrongRequestFormatException("Failed to deserialize JSON: ", e);
        }
    }

    @Override
    public AnimeListPayload deserializeAnimeIdList(String json) {
        try {
            return mapper.readValue(json, AnimeListPayload.class);
        } catch (JsonProcessingException e) {
            log.error("Failed using mapper to read JSON: {}", e.getMessage());
            log.debug("JSON that caused the exception: {}", json);
            throw new WrongRequestFormatException("Failed to deserialize JSON: ", e);
        }
    }
}
