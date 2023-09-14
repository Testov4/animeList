package ms.animeservice.service;

import ms.animeservice.model.Anime;
import ms.animeservice.util.AnimeSearchRequest;
import ms.animeservice.util.dto.AnimeDto;
import ms.animeservice.util.dto.CompressedAnimeDto;

import java.util.List;

public interface AnimeService {

    List<Anime> findAnimeListByIds(List<Integer> ids);

    List<CompressedAnimeDto> findAnimeByTitleAndTypeAndGenres(AnimeSearchRequest animeSearchRequest);

    void saveNotPresentAnimeList(List<AnimeDto> animeDtoList);

    AnimeDto findAnimeByIdAndFetchAll(Integer id);
}
