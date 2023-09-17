package ms.animeservice.service;

import ms.animeservice.payload.AnimeListPayload;
import ms.animeservice.payload.SingleAnimePayload;
import ms.animeservice.model.dto.AnimeDto;
import ms.animeservice.payload.AnimeSearchPayload;
import java.util.List;

public interface DeserializerService {
    AnimeSearchPayload deserializeAnimeSearchRequest(String json);

    List<AnimeDto> deserializeAnimeList(String json);

    SingleAnimePayload deserializeAnimeId(String json);

    AnimeListPayload deserializeAnimeIdList(String json);

}
