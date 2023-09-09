package ms.animedbservice.service;

import ms.animedbservice.dto.AnimeDto;
import ms.animedbservice.model.Anime;
import java.util.List;

public interface DtoToAnimeConvertService {

    Anime convertAnimeDtoToEntity(AnimeDto animeDto);

    List<Anime> convertDtoListToAnimeList(List<AnimeDto> animeList);
}
