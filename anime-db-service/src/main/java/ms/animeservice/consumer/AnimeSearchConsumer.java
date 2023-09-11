package ms.animeservice.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.animeservice.model.Anime;
import ms.animeservice.util.AnimeSearchRequest;
import ms.animeservice.service.AnimeService;
import ms.animeservice.service.DeserializerService;
import ms.animeservice.service.KafkaService;
import ms.animeservice.util.dto.CompressedAnimeDto;
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
        AnimeSearchRequest request = deserializerService.deserializeAnimeSearchRequest(data);
        log.info("Data deserialized: {}", request);
        List<Anime> animeList = animeService.findAnimeByTitleAndTypeAndGenres(request);
        log.info("List from DB received: {}", animeList);
        List<CompressedAnimeDto> dtoList =
            modelMapper.map(animeList, new TypeToken<List<CompressedAnimeDto>>(){}.getType());
        log.info("converted List from DB to compressed DTO: {}", dtoList);
        kafkaService.sendCompressedAnimeList(dtoList);
        log.info("Message sent: {}", animeList);
        kafkaService.sendSearchRequest(request);
        log.info("Message sent: {}", request);
    }

}
