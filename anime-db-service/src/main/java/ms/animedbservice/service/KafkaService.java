package ms.animedbservice.service;

import ms.animedbservice.dto.AnimeDto;
import ms.animedbservice.model.AnimeSearchRequest;
import java.util.List;

public interface KafkaService {
    public void sendMessage(AnimeSearchRequest data);

    public void sendMessage(List<AnimeDto> data);
}
