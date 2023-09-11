package ms.animeservice.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.animeservice.model.Anime;
import ms.animeservice.service.AnimeService;
import ms.animeservice.service.DeserializerService;
import ms.animeservice.service.KafkaService;
import ms.animeservice.util.dto.AnimeDto;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = "${spring.kafka.topic.name.consume-anime-page}")
public class AnimePageConsumer {

    private final AnimeService animeService;

    private final DeserializerService deserializerService;

    private final KafkaService kafkaService;

    private final ModelMapper modelMapper;

    @KafkaHandler
    void listenAnimeSearchRequest(String data) {
        Integer animeId = deserializerService.deserializeAnimeId(data);
        log.info("Data deserialized: {}", animeId);
        Anime anime = animeService.findAnimeByIdAndFetchAll(animeId);
        log.info("got Anime entity from DB: {}", anime);
        AnimeDto animeDto = modelMapper.map(anime, AnimeDto.class);
        log.info("Entity converted to DTO: {}", animeDto);
        kafkaService.sendFullSingleAnime(animeDto);
        log.info("Message sent: {}", animeDto);
    }
}
