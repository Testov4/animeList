package ms.animedbservice.service;

import ms.animedbservice.dto.AnimeDto;
import ms.animedbservice.model.Anime;
import java.util.List;

public interface AnimeToDtoConvertService {
    AnimeDto convertToAnimeDto(Anime anime);

    List<AnimeDto> convertAnimeListToDtoList(List<Anime> animeList);
}
