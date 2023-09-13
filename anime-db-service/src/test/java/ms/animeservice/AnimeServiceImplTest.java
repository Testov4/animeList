package ms.animeservice;

import ms.animeservice.model.Anime;
import ms.animeservice.util.AnimeSearchRequest;
import ms.animeservice.model.Genre;
import ms.animeservice.repository.AnimeRepository;
import ms.animeservice.repository.GenreRepository;
import ms.animeservice.service.implementation.AnimeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.List;

public class AnimeServiceImplTest {

    @Mock
    private AnimeRepository animeRepository;

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private AnimeServiceImpl animeDbService;

    static List<Anime> foundList;

    static List<Genre> genres;

    static AnimeSearchRequest animeRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void prepareFoundList() {
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
    }

    @BeforeEach
    public void prepareAnimeSearchRequest() {
        animeRequest = new AnimeSearchRequest();
        animeRequest.setGenres(List.of());
        animeRequest.setType("");
        animeRequest.setTitle("test_title");
    }

    @Test
    public void findAnimeByParameters_shouldReturnEmptyList_whenRepositoryReturnsEmptyList() {
        Mockito.when(animeRepository.findByTitleContainingIgnoreCase(animeRequest.getTitle()))
            .thenReturn(List.of());
        List<Anime> result = animeDbService.findAnimeByTitleAndTypeAndGenres(animeRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, List.of());
    }

    @Test
    public void findAnimeByParameters_shouldReturnEmptyList_whenWrongTypeIsPassed() {
        Mockito.when(animeRepository.findByTitleContainingIgnoreCase(animeRequest.getTitle()))
            .thenReturn(foundList);
        animeRequest.setType("wrong_type");
        List<Anime> result = animeDbService.findAnimeByTitleAndTypeAndGenres(animeRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, List.of());
    }

    @Test
    public void findAnimeByParameters_shouldReturnEmptyList_whenWrongGenreIsPassed() {
        animeRequest.setGenres(List.of(300));
        Mockito.when(animeRepository.findByTitleContainingIgnoreCase(animeRequest.getTitle()))
            .thenReturn(foundList);
        Mockito.when(genreRepository.findAllById(Mockito.any()))
            .thenReturn(List.of(new Genre()));


        List<Anime> result = animeDbService.findAnimeByTitleAndTypeAndGenres(animeRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(List.of(), result);
    }

    @Test
    public void findAnimeByParameters_shouldReturnFullList_whenRepositoryReturnsFullListAndOtherParametersAreNotPassed() {
        Mockito.when(animeRepository.findByTitleContainingIgnoreCase(animeRequest.getTitle()))
            .thenReturn(foundList);
        List<Anime> result = animeDbService.findAnimeByTitleAndTypeAndGenres(animeRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, foundList);
    }

    @Test
    public void findAnimeByParameters_shouldReturnListOf2_whenRepositoryReturnFullListAndTestType1IsPassed() {
        animeRequest.setType("test_type1");
        Mockito.when(animeRepository.findByTitleContainingIgnoreCase(animeRequest.getTitle()))
            .thenReturn(foundList);
        List<Anime> result = animeDbService.findAnimeByTitleAndTypeAndGenres(animeRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(), 2);
        Assertions.assertEquals(result, List.of(foundList.get(0), foundList.get(1)));
    }

    @Test
    public void findAnimeByParameters_shouldReturnListOf2_whenRepositoryReturnFullListWhenTestGenre0IsPassed() {
        Mockito.when(animeRepository.findByTitleContainingIgnoreCase(animeRequest.getTitle()))
            .thenReturn(foundList);
        Mockito.when(genreRepository.findAllById(Mockito.any()))
            .thenReturn(List.of(genres.get(0)));
        List<Anime> result = animeDbService.findAnimeByTitleAndTypeAndGenres(animeRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(), 2);
        Assertions.assertEquals(result, List.of(foundList.get(0), foundList.get(1)));
    }

    @Test
    public void findAnimeByParameters_shouldReturnListOf1_whenRepositoryReturnFullListAndWhenTestGenre1AndTestType2ArePassed() {
        animeRequest.setType("test_type2");
        Mockito.when(animeRepository.findByTitleContainingIgnoreCase(animeRequest.getTitle()))
            .thenReturn(foundList);
        Mockito.when(genreRepository.findAllById(Mockito.any()))
            .thenReturn(List.of(genres.get(1)));
        List<Anime> result = animeDbService.findAnimeByTitleAndTypeAndGenres(animeRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(), 1);
        Assertions.assertEquals(result, List.of(foundList.get(2)));
    }



}
