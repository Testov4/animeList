package ms.animeservice.service;

import ms.animeservice.payload.AnimeListRequest;
import ms.animeservice.payload.SingleAnimeRequest;
import ms.animeservice.model.dto.AnimeDto;
import ms.animeservice.payload.AnimeSearchRequest;
import java.util.List;

public interface DeserializerService {
    AnimeSearchRequest deserializeAnimeSearchRequest(String json);

    List<AnimeDto> deserializeAnimeList(String json);

    SingleAnimeRequest deserializeAnimeId(String json);

    AnimeListRequest deserializeAnimeIdList(String json);

}
