package ms.animedbservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.animedbservice.dto.AnimeDto;
import ms.animedbservice.model.Anime;
import ms.animedbservice.service.AnimeDbService;
import ms.animedbservice.service.AnimeToDtoConvertService;
import ms.animedbservice.service.DeserializerService;
import ms.animedbservice.service.DtoToAnimeConvertService;
import ms.animedbservice.service.KafkaService;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = "${spring.kafka.topic.name.consume-api-search-response}")
public class ApiResponseConsumer {

    private final AnimeDbService animeDbService;

    private final DeserializerService deserializerService;

    private final KafkaService kafkaService;

    private final DtoToAnimeConvertService dtoToAnimeConvertService;

    private final AnimeToDtoConvertService animeToDtoConvertService;

    @KafkaHandler
    void listenAnimeSearchRequest(String data) {
        List<AnimeDto> animeDtos = deserializerService.deserializeAnimeList(data);
        log.info("Data deserialized: {}", animeDtos);
        List<Anime> animeList = dtoToAnimeConvertService.convertDtoListToAnimeList(animeDtos);
        log.info("DTO Data converted to entity: {}", animeList);
        animeDbService.saveNotPresentAnimeList(animeList);
        log.info("List injected into DB");
        kafkaService.sendMessage(animeDtos);
        log.info("Message sent: {}", animeDtos);
    }

}
