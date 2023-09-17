package ms.animeservice;

import ms.animeservice.model.Anime;
import ms.animeservice.payload.AnimeSearchRequest;
import ms.animeservice.model.Genre;
import ms.animeservice.repository.AnimeRepository;
import ms.animeservice.repository.GenreRepository;
import ms.animeservice.service.implementation.AnimeServiceImpl;
import ms.animeservice.model.dto.PartialAnimeDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;

public class AnimeServiceImplTest {

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private AnimeRepository animeRepository;

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private AnimeServiceImpl animeDbService;

    static List<Anime> foundList;

    static List<PartialAnimeDto> foundListCompressedDto;

    static List<Genre> genres;

    static AnimeSearchRequest animeRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void prepareFoundList() {
        ModelMapper modelMapper = new ModelMapper();

        genres= List.of(Genre.builder()
                .malId(1)
                .name("test_genre1")
                .build(),
            Genre.builder()
                .malId(2)
                .name("test_genre2")
                .build(),
            Genre.builder()
                .malId(3)
                .name("test_genre3")
                .build());
        foundList = List.of(
            Anime.builder()
                .malId(1)
                .title("test_title1")
                .type("test_type1")
                .genres(genres)
                .build(),
            Anime.builder()
                .malId(2)
                .title("test_title2")
                .type("test_type1")
                .genres(List.of(genres.get(0)))
                .build(),
            Anime.builder()
                .malId(3)
                .title("test_title2")
                .type("test_type2")
                .genres(List.of(genres.get(1)))
                .build()
        );

        foundListCompressedDto = modelMapper.map(foundList, new TypeToken<List<PartialAnimeDto>>(){}.getType());
    }

    @BeforeEach
    public void prepareAnimeSearchRequest() {
        animeRequest = new AnimeSearchRequest();
        animeRequest.setGenres(List.of());
        animeRequest.setType("");
        animeRequest.setTitle("test_title");
    }

    @Test
    public void findAnimeByTitleAndTypeAndGenres_shouldReturnEmptyList_whenRepositoryReturnsEmptyList() {
        Mockito.when(animeRepository.findByTitleContains(animeRequest.getTitle()))
            .thenReturn(List.of());
        List<Anime> result = animeDbService.findAnimeByTitleAndTypeAndGenres(animeRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, List.of());
    }

    @Test
    public void findAnimeByTitleAndTypeAndGenres_shouldReturnEmptyList_whenWrongTypeIsPassed() {
        Mockito.when(animeRepository.findByTitleContains(animeRequest.getTitle()))
            .thenReturn(foundList);
        animeRequest.setType("wrong_type");
        List<Anime> result = animeDbService.findAnimeByTitleAndTypeAndGenres(animeRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, List.of());
    }

    @Test
    public void findAnimeByTitleAndTypeAndGenres_shouldReturnEmptyList_whenWrongGenreIsPassed() {
        animeRequest.setGenres(List.of(300));
        Mockito.when(animeRepository.findByTitleContains(animeRequest.getTitle()))
            .thenReturn(foundList);
        Mockito.when(genreRepository.findAllById(Mockito.any()))
            .thenReturn(List.of(new Genre()));


        List<Anime> result = animeDbService.findAnimeByTitleAndTypeAndGenres(animeRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, List.of());
    }

    @Test
    public void findAnimeByTitleAndTypeAndGenres_shouldReturnFullList_whenOnlyTitlePassed() {
        Mockito.when(animeRepository.findByTitleContains(animeRequest.getTitle()))
            .thenReturn(foundList);
        List<Anime> result = animeDbService.findAnimeByTitleAndTypeAndGenres(animeRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, foundList);
    }

    @Test
    public void findAnimeByTitleAndTypeAndGenres_shouldReturnListOf2_whenTestType1IsPassed() {
        animeRequest.setType("test_type1");
        Mockito.when(animeRepository.findByTitleContains(animeRequest.getTitle()))
            .thenReturn(foundList);
        List<Anime> result = animeDbService.findAnimeByTitleAndTypeAndGenres(animeRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(), 2);
        Assertions.assertEquals(result, List.of(foundList.get(0), foundList.get(1)));
    }

    @Test
    public void findAnimeByTitleAndTypeAndGenres_shouldReturnListOf2_whenTestGenre0IsPassed() {
        Mockito.when(animeRepository.findByTitleContains(animeRequest.getTitle()))
            .thenReturn(foundList);
        Mockito.when(genreRepository.findAllById(Mockito.any()))
            .thenReturn(List.of(genres.get(0)));
        List<Anime> result = animeDbService.findAnimeByTitleAndTypeAndGenres(animeRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(), 2);
        Assertions.assertEquals(result, List.of(foundList.get(0), foundList.get(1)));
    }

    @Test
    public void findAnimeByTitleAndTypeAndGenres_shouldReturnListOf1_whenTestGenre1AndTestType2ArePassed() {
        animeRequest.setType("test_type2");
        Mockito.when(animeRepository.findByTitleContains(animeRequest.getTitle()))
            .thenReturn(foundList);
        Mockito.when(genreRepository.findAllById(Mockito.any()))
            .thenReturn(List.of(genres.get(1)));
        List<Anime> result = animeDbService.findAnimeByTitleAndTypeAndGenres(animeRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(), 1);
        Assertions.assertEquals(result, List.of(foundList.get(2)));
    }



}
