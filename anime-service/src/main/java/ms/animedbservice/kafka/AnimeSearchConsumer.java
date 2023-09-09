package ms.animedbservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.animedbservice.dto.AnimeDto;
import ms.animedbservice.model.Anime;
import ms.animedbservice.model.AnimeSearchRequest;
import ms.animedbservice.service.AnimeDbService;
import ms.animedbservice.service.AnimeToDtoConvertService;
import ms.animedbservice.service.DeserializerService;
import ms.animedbservice.service.KafkaService;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = "${spring.kafka.topic.name.consume-anime-search}")
public class AnimeSearchConsumer {

    private final AnimeDbService animeDbService;

    private final DeserializerService deserializerService;

    private final KafkaService kafkaService;

    private final AnimeToDtoConvertService animeToDtoConvertService;

    @KafkaHandler
    void listenAnimeSearchRequest(String data) {
        AnimeSearchRequest request = deserializerService.deserializeAnimeSearchRequest(data);
        log.info("Data deserialized: {}", request);
        List<Anime> animeList = animeDbService.findAnimeByParameters(request);
        log.info("List from DB received: {}", animeList);
        List<AnimeDto> dtoList = animeToDtoConvertService.convertAnimeListToDtoList(animeList);
        log.info("converted List from DB to DTO: {}", dtoList);
        kafkaService.sendMessage(dtoList);
        log.info("Message sent: {}", animeList);
        kafkaService.sendMessage(request);
        log.info("Message sent: {}", request);
    }

}
