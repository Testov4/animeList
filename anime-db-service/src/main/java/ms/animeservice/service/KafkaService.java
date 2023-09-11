package ms.animeservice.service;

import ms.animeservice.util.AnimeSearchRequest;
import ms.animeservice.util.dto.AnimeDto;
import ms.animeservice.util.dto.CompressedAnimeDto;
import java.util.List;

public interface KafkaService {
    void sendSearchRequest(AnimeSearchRequest data);

    void sendCompressedAnimeList(List<CompressedAnimeDto> data);

    void sendFullSingleAnime(AnimeDto data);
}
