package ms.animedbservice.model;

import lombok.Data;
import java.util.List;
import java.util.Optional;

@Data
public class AnimeSearchRequest {

    String q;
    Optional<String> type;
    List<Integer> genres;

}
