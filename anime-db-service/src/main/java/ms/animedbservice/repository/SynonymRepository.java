package ms.animedbservice.repository;

import ms.animedbservice.model.TitleSynonym;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SynonymRepository extends JpaRepository<TitleSynonym, Long> {
}
