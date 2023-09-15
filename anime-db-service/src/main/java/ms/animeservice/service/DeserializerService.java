package ms.animeservice.service;

import ms.animeservice.util.SingleAnimeRequest;
import ms.animeservice.util.dto.AnimeDto;
import ms.animeservice.util.AnimeSearchRequest;
import java.util.List;

public interface DeserializerService {
    AnimeSearchRequest deserializeAnimeSearchRequest(String json);

    List<AnimeDto> deserializeAnimeList(String json);

    SingleAnimeRequest deserializeAnimeId(String json);

}
