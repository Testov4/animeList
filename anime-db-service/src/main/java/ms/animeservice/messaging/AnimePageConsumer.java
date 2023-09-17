package ms.animeservice.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.animeservice.model.Anime;
import ms.animeservice.service.AnimeService;
import ms.animeservice.service.DeserializerService;
import ms.animeservice.service.KafkaService;
import ms.animeservice.payload.SingleAnimePayload;
import ms.animeservice.model.dto.AnimeDto;
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
    void listenAnimePageRequest(String data) {
        SingleAnimePayload animeRequest = deserializerService.deserializeAnimeId(data);
        log.info("Data deserialized: {}", animeRequest);
        Anime anime = animeService.findAnimeByIdAndFetchAll(animeRequest.getId());
        log.info("got Anime from DB: {}", anime);
        AnimeDto animeDto = modelMapper.map(anime, AnimeDto.class);
        log.info("Entity converted to DTO: {}", animeDto);
        kafkaService.sendFullSingleAnime(animeDto);
        log.info("Message sent: {}", animeDto);
    }
}
