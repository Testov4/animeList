package ms.animeservice.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.animeservice.model.Anime;
import ms.animeservice.payload.AnimeSearchPayload;
import ms.animeservice.service.AnimeService;
import ms.animeservice.service.DeserializerService;
import ms.animeservice.service.KafkaService;
import ms.animeservice.model.dto.PartialAnimeDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = "${spring.kafka.topic.name.consume-anime-search}")
public class AnimeSearchConsumer {

    private final AnimeService animeService;

    private final DeserializerService deserializerService;

    private final KafkaService kafkaService;

    private final ModelMapper modelMapper;

    @KafkaHandler
    void listenAnimeSearchRequest(String data) {
        AnimeSearchPayload request = deserializerService.deserializeAnimeSearchRequest(data);
        log.info("Data deserialized: {}", request);
        List<Anime> animeList= animeService.findAnimeByTitleAndTypeAndGenres(request);
        log.info("List from DB received: {}", animeList);
        List<PartialAnimeDto> animeDtoList =
            modelMapper.map(animeList, new TypeToken<List<PartialAnimeDto>>(){}.getType());
        log.info("Entity List converted to DTO: {}", animeDtoList);
        kafkaService.sendFoundAnimeList(animeDtoList);
        log.info("Message sent: {}", animeDtoList);
        kafkaService.sendSearchRequest(request);
        log.info("Message sent: {}", request);
    }

}
