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
import ms.animedbservice.service.DtoToAnimeConvertService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DtoToAnimeConvertServiceImpl implements DtoToAnimeConvertService {
    @Override
    public Anime convertAnimeDtoToEntity(AnimeDto animeDto) {
        Anime anime = new Anime();
        BeanUtils.copyProperties(animeDto, anime);
        anime.setImages(convertImageDtoListToAnimeImageList(animeDto.getImages(), anime));
        anime.setTitleSynonyms(convertTitleSynonymsDtoToEntity(animeDto.getTitleSynonyms(), anime));
        anime.setGenres(convertGenresDtoToEntity(animeDto.getGenres()));
        anime.setStudios(convertStudiosDtoToEntity(animeDto.getStudios()));
        return anime;
    }

    @Override
    public List<Anime> convertDtoListToAnimeList(List<AnimeDto> animeList) {
        return animeList.stream()
            .map(animeDto -> convertAnimeDtoToEntity(animeDto))
            .collect(Collectors.toList());
    }

    private List<AnimeImage> convertImageDtoListToAnimeImageList(Map<String, ImageDto> imageDtoMap, Anime anime) {
        List<AnimeImage> animeImages = new ArrayList<>();

        for (Map.Entry<String, ImageDto> entry : imageDtoMap.entrySet()) {
            String imageType = entry.getKey();
            ImageDto imageDto = entry.getValue();

            AnimeImage animeImage = new AnimeImage();
            animeImage.setImageType(imageType);
            animeImage.setImageUrl(imageDto.getUrl());
            animeImage.setImageLargeUrl(imageDto.getLargeUrl());
            animeImage.setImageSmallUrl(imageDto.getSmallUrl());

            // setting anime reference.
            animeImage.setAnime(anime);

            animeImages.add(animeImage);
        }

        return animeImages;
    }

    private List<TitleSynonym> convertTitleSynonymsDtoToEntity(List<String> titleSynonyms, Anime anime) {
        List<TitleSynonym> synonimsList = new ArrayList<>();

        titleSynonyms.forEach(titleSynonym -> {
            TitleSynonym entityTitleSynonym = new TitleSynonym();
            entityTitleSynonym.setSynonym(titleSynonym);
            entityTitleSynonym.setAnime(anime);
            synonimsList.add(entityTitleSynonym);
        });

        return synonimsList;
    }

    private List<Genre> convertGenresDtoToEntity(List<GenreDto> genreDtoList) {
        List<Genre> genreList = new ArrayList<>();

        genreDtoList.forEach(genreDto -> {
            Genre genre = new Genre();
            BeanUtils.copyProperties(genreDto, genre);
            genreList.add(genre);
        });

        return genreList;
    }

    private List<Studio> convertStudiosDtoToEntity(List<StudioDto> studioDtoList) {
        List<Studio> studioList = new ArrayList<>();

        studioDtoList.forEach(studioDto -> {
            Studio studio = new Studio();
            BeanUtils.copyProperties(studioDto, studio);
            studioList.add(studio);
        });

        return studioList;
    }
}
