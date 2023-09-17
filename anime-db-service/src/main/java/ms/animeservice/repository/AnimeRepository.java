package ms.animeservice.repository;

import ms.animeservice.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Integer> {

    @Query("SELECT a FROM Anime a LEFT JOIN FETCH a.images e WHERE UPPER(a.title) LIKE UPPER(concat('%', :animeTitle, '%') ) " )
    List<Anime> findByTitleContains(@Param("animeTitle") String title);

    @Query("SELECT a.malId FROM Anime a WHERE a IN :animeList")
    List<Integer> findPresentAnimeList(@Param("animeList") List<Anime> animeList);

    @Query("SELECT a FROM Anime a LEFT JOIN FETCH a.images e WHERE a.malId IN :animeIds")
    List<Anime> findAllByMalId(@Param("animeIds") List<Integer> ids);

}
