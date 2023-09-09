package ms.animedbservice.repository;

import ms.animedbservice.model.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudioRepository extends JpaRepository<Studio, Integer> {
}
