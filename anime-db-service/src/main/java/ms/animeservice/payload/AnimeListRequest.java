package ms.animeservice.payload;

import lombok.Data;

import java.util.List;

@Data
public class AnimeListRequest {

    List<Integer> ids;

}
