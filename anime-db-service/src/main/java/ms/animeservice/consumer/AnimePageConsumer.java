package ms.animeservice.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.animeservice.service.AnimeService;
import ms.animeservice.service.DeserializerService;
import ms.animeservice.service.KafkaService;
import ms.animeservice.util.SingleAnimeRequest;
import ms.animeservice.util.dto.AnimeDto;
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

    @KafkaHandler
    void listenAnimeSearchRequest(String data) {
        SingleAnimeRequest animeRequest = deserializerService.deserializeAnimeId(data);
        log.info("Data deserialized: {}", animeRequest);
        AnimeDto animeDto = animeService.findAnimeByIdAndFetchAll(animeRequest.getId());
        log.info("got Anime entity from DB: {}", animeDto);
        kafkaService.sendFullSingleAnime(animeDto);
        log.info("Message sent: {}", animeDto);
    }
}
