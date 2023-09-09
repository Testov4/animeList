package ms.animedbservice.repository;

import ms.animedbservice.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Integer> {
    List<Anime> findByTitleLikeIgnoreCase(String title);
}
