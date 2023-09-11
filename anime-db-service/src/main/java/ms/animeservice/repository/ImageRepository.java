package ms.animeservice.repository;

import ms.animeservice.model.AnimeImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<AnimeImage, Long> {
}
