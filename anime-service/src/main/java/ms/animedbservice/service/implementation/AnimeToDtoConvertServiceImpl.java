package ms.animedbservice.service.implementation;

import lombok.RequiredArgsConstructor;
import ms.animedbservice.dto.AnimeDto;
import ms.animedbservice.dto.GenreDto;
import ms.animedbservice.dto.ImageDto;
import ms.animedbservice.dto.StudioDto;
import ms.animedbservice.model.Anime;
import ms.animedbservice.model.AnimeImage;
import ms.animedbservice.model.Genre;
import ms.animedbservice.model.Studio;
import ms.animedbservice.model.TitleSynonym;
import ms.animedbservice.service.AnimeToDtoConvertService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimeToDtoConvertServiceImpl implements AnimeToDtoConvertService {
    @Override
    public AnimeDto convertToAnimeDto(Anime anime) {
        AnimeDto animeDto = new AnimeDto();
        BeanUtils.copyProperties(anime, animeDto);
        animeDto.setImages(convertAnimeImagesToImageDtoMap(anime.getImages()));
        animeDto.setTitleSynonyms(convertTitleSynonymsEntityToDto(anime.getTitleSynonyms()));
        animeDto.setGenres(convertGenresEntityToDto(anime.getGenres()));
        animeDto.setStudios(convertStudiosEntityToDto(anime.getStudios()));
        return animeDto;
    }

    @Override
    public List<AnimeDto> convertAnimeListToDtoList(List<Anime> animeList) {
        return animeList.stream()
            .map(anime -> convertToAnimeDto(anime))
            .collect(Collectors.toList());
    }

    private Map<String, ImageDto> convertAnimeImagesToImageDtoMap(List<AnimeImage> animeImages) {
        Map<String, ImageDto> imageDtoMap = new HashMap<>();

        for (AnimeImage animeImage : animeImages) {
            String imageType = animeImage.getImageType();
            ImageDto imageDto = new ImageDto();
            imageDto.setUrl(animeImage.getImageUrl());
            imageDto.setLargeUrl(animeImage.getImageLargeUrl());
            imageDto.setSmallUrl(animeImage.getImageSmallUrl());
            imageDtoMap.put(imageType, imageDto);
        }

        return imageDtoMap;
    }

    private List<String> convertTitleSynonymsEntityToDto(List<TitleSynonym> titleSynonyms) {
        List<String> titleSynonymList = new ArrayList<>();

        for (TitleSynonym entityTitleSynonym : titleSynonyms) {
            titleSynonymList.add(entityTitleSynonym.getSynonym());
        }

        return titleSynonymList;
    }

    private List<GenreDto> convertGenresEntityToDto(List<Genre> genreList) {
        List<GenreDto> genreDtoList = new ArrayList<>();

        for (Genre genre : genreList) {
            GenreDto genreDto = new GenreDto();
            BeanUtils.copyProperties(genre, genreDto);
            genreDtoList.add(genreDto);
        }

        return genreDtoList;
    }

    private List<StudioDto> convertStudiosEntityToDto(List<Studio> studioList) {
        List<StudioDto> studioDtoList = new ArrayList<>();

        for (Studio studio : studioList) {
            StudioDto studioDto = new StudioDto();
            BeanUtils.copyProperties(studio, studioDto);
            studioDtoList.add(studioDto);
        }

        return studioDtoList;
    }
}
