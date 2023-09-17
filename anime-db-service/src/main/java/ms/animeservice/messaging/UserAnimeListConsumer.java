package ms.animeservice.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.animeservice.model.Anime;
import ms.animeservice.model.dto.PartialAnimeDto;
import ms.animeservice.payload.AnimeListPayload;
import ms.animeservice.service.AnimeService;
import ms.animeservice.service.DeserializerService;
import ms.animeservice.service.KafkaService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = "${spring.kafka.topic.name.consume-anime-list-request}")
public class UserAnimeListConsumer {

    private final AnimeService animeService;

    private final DeserializerService deserializerService;

    private final KafkaService kafkaService;

    private final ModelMapper modelMapper;

    @KafkaHandler
    void listenAnimeSearchRequest(String data) {
        AnimeListPayload animeListPayload = deserializerService.deserializeAnimeIdList(data);
        log.info("Data deserialized: {}", animeListPayload);
        List<Anime> animeList = animeService.findAnimeListByIds(animeListPayload.getIds());
        log.info("got Anime from DB: {}", animeList);
        List<PartialAnimeDto> animeDtoList =
            modelMapper.map(animeList, new TypeToken<List<PartialAnimeDto>>(){}.getType());
        log.info("Entity converted to DTO: {}", animeDtoList);
        kafkaService.sendUserAnimeList(animeDtoList);
        log.info("Message sent: {}", animeDtoList);
    }
}
