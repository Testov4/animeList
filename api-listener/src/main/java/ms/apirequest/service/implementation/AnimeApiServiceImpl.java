package ms.apirequest.service.implementation;

import ms.apirequest.exception.ApiErrorResponseException;
import ms.apirequest.model.Anime;
import ms.apirequest.openfeign.AnimeRequestClient;
import ms.apirequest.service.AnimeApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnimeApiServiceImpl implements AnimeApiService {

    private final AnimeRequestClient animeRequestClient;

    @Override
    public List<Anime> requestAnimeListByParameters(Map<String, Object> animeSearchRequest) {
        try {
            return animeRequestClient.searchAnime(animeSearchRequest).getData();
        } catch (ApiErrorResponseException e) {
            log.error("Api response contains error: {}", e.getMessage());
            throw new RuntimeException();
        }
    }
}
