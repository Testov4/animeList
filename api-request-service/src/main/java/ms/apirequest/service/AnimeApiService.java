package ms.apirequest.service;

import ms.apirequest.model.Anime;
import java.util.List;
import java.util.Map;

public interface AnimeApiService {

    List<Anime> requestAnimeListByParameters(Map<String, Object> animeSearchRequest);
}
