package ms.apirequest.kafka;

import ms.apirequest.model.Anime;
import ms.apirequest.service.AnimeApiService;
import ms.apirequest.service.RequestDeserializerService;
import ms.apirequest.service.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = "${spring.kafka.topic.name.consume}")
public class AnimeSearchConsumer {

    private final AnimeApiService animeApiService;

    private final RequestDeserializerService requestDeserializerService;

    private final KafkaService kafkaService;

    @KafkaHandler
    void listenAnimeSearchRequest(String data) {
        Map request = requestDeserializerService.deserializeAnimeSearchRequest(data);
        log.info("Data deserialized: {}", request);
        List<Anime> animeList = animeApiService.requestAnimeListByParameters(request);
        log.info("List from API received: {}", animeList);
        kafkaService.sendMessage(animeList);
        log.info("Message sent: {}", animeList);
    }
}
