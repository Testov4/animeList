package ms.apirequest;

import ms.apirequest.exception.ApiErrorResponseException;
import ms.apirequest.model.Anime;
import ms.apirequest.model.ApiResponse;
import ms.apirequest.openfeign.AnimeRequestClient;
import ms.apirequest.service.implementation.AnimeApiServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AnimeApiServiceImplTest {

    static Map<String, Object> testRequest;

    private final String apiUrl = "https://api.jikan.moe/v4/anime";

    private ApiResponse responseWithList;

    @Mock
    private AnimeRequestClient animeRequestClient;

    @InjectMocks
    private AnimeApiServiceImpl animeApiService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testRequest = Map.of("q", "test_title", "genres", "", "producers", "", "type", "");
    }

    @BeforeAll
    void createResponseWithList() {
        responseWithList = new ApiResponse();
        responseWithList.setData(List.of(
            Anime.builder()
                .malId(1)
                .title("test_title_1")
                .type("tv")
                .build(),
            Anime.builder()
                .malId(2)
                .title("test_title_2")
                .type("tv")
                .build(),
            Anime.builder()
                .malId(3)
                .title("test_title_3")
                .type("tv")
                .build()
            ));
    }

    @Test
    public void requestAnimeListByParameters_shouldReturnList_whenDataIsPresent() {
        Mockito.when(animeRequestClient.searchAnime(testRequest)).
            thenReturn(responseWithList);
        List<Anime> result = animeApiService.requestAnimeListByParameters(testRequest);

        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(result, responseWithList.getData());
    }

}
