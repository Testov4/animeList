package ms.animeservice.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.animeservice.model.Anime;
import ms.animeservice.model.dto.PartialAnimeDto;
import org.modelmapper.ModelMapper;
import ms.animeservice.model.dto.AnimeDto;
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
    void listenApiResponse(String data) {
        List<AnimeDto> animeDtoList = deserializerService.deserializeAnimeList(data);
        log.info("Data deserialized: {}", animeDtoList);
        List<Anime> animeList =
            modelMapper.map(animeDtoList, new TypeToken<List<Anime>>(){}.getType());
        animeService.saveNotPresentAnimeList(animeList);
        log.info("List inserted into DB");
        List<PartialAnimeDto> partialAnimeDtos =
            modelMapper.map(animeDtoList, new TypeToken<List<PartialAnimeDto>>(){}.getType());
        kafkaService.sendFoundAnimeList(partialAnimeDtos);
        log.info("Message sent: {}", animeDtoList);
    }

}
