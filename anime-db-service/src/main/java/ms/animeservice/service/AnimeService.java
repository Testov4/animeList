package ms.animeservice.service;

import ms.animeservice.model.Anime;
import ms.animeservice.payload.AnimeSearchPayload;

import java.util.List;

public interface AnimeService {

    List<Anime> findAnimeListByIds(List<Integer> ids);

    List<Anime> findAnimeByTitleAndTypeAndGenres(AnimeSearchPayload animeSearchPayload);

    void saveNotPresentAnimeList(List<Anime> animeList);

    Anime findAnimeByIdAndFetchAll(Integer id);
}
