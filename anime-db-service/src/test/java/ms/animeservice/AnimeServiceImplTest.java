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
import java.util.Optional;

public class AnimeServiceImplTest {

    @Mock
    private AnimeRepository animeRepository;

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private AnimeServiceImpl animeDbService;

    static List<Anime> foundList;

    static AnimeSearchRequest animeRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void prepareFoundList() {
        List<Genre> genres= List.of(Genre.builder()
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
        animeRequest.setGenres(List.of(1));
        Mockito.when(animeRepository.findByTitleContainingIgnoreCase(animeRequest.getTitle()))
            .thenReturn(foundList);
        Mockito.when(genreRepository.findById(Mockito.any()))
            .thenReturn(Optional.of(new Genre()));


        List<Anime> result = animeDbService.findAnimeByTitleAndTypeAndGenres(animeRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, List.of());
    }


}
