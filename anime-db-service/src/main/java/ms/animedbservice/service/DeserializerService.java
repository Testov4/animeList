package ms.animedbservice.service;

import ms.animedbservice.dto.AnimeDto;
import ms.animedbservice.model.AnimeSearchRequest;
import java.util.List;

public interface DeserializerService {
    AnimeSearchRequest deserializeAnimeSearchRequest(String json);

    List<AnimeDto> deserializeAnimeList(String json);

}
