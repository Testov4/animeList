package ms.animedbservice.service;

import ms.animedbservice.model.Anime;
import ms.animedbservice.model.AnimeSearchRequest;
import java.util.List;

public interface AnimeDbService {

    List<Anime> findAnimeListByIds(List<Integer> ids);

    List<Anime> findAnimeByParameters(AnimeSearchRequest animeSearchRequest);

    void saveNotPresentAnimeList(List<Anime> animeList);
}
