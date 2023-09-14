package ms.animeservice.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.animeservice.util.dto.CompressedAnimeDto;
import org.modelmapper.ModelMapper;
import ms.animeservice.util.dto.AnimeDto;
import ms.animeservice.service.AnimeService;
import ms.animeservice.service.DeserializerService;
import ms.animeservice.service.KafkaService;
import org.modelmapper.TypeToken;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = "${spring.kafka.topic.name.consume-api-search-response}")
public class ApiResponseConsumer {

    private final AnimeService animeService;

    private final DeserializerService deserializerService;

    private final KafkaService kafkaService;

    private final ModelMapper modelMapper;

    @KafkaHandler
    void listenAnimeSearchRequest(String data) {
        List<AnimeDto> animeDtoList = deserializerService.deserializeAnimeList(data);
        log.info("Data deserialized: {}", animeDtoList);
        animeService.saveNotPresentAnimeList(animeDtoList);
        log.info("List inserted into DB");
        List<CompressedAnimeDto> compressedAnimeDtos =
            modelMapper.map(animeDtoList, new TypeToken<List<CompressedAnimeDto>>(){}.getType());
        kafkaService.sendCompressedAnimeList(compressedAnimeDtos);
        log.info("Message sent: {}", animeDtoList);
    }

}
