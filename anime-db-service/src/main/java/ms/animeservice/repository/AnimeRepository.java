package ms.animeservice.repository;

import ms.animeservice.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Integer> {
    List<Anime> findByTitleContainingIgnoreCase(String title);
}
