package ms.animeservice.repository;

import ms.animeservice.model.TitleSynonym;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SynonymRepository extends JpaRepository<TitleSynonym, Long> {
}
