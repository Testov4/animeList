package ms.animedbservice.repository;

import ms.animedbservice.model.AnimeImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<AnimeImage, Long> {
}
