package ms.animeservice.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.animeservice.util.dto.AnimeDto;
import ms.animeservice.util.AnimeSearchRequest;
import ms.animeservice.service.KafkaService;
import ms.animeservice.util.dto.CompressedAnimeDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaServiceImpl implements KafkaService {

    @Value("${spring.kafka.topic.name.produce-api-request}")
    private String apiRequestTopicName;

    @Value("${spring.kafka.topic.name.produce-anime-list}")
    private String animeListTopicName;

    @Value("${spring.kafka.topic.name.produce-anime-single}")
    private String singleAnimeTopicName;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendSearchRequest(AnimeSearchRequest data) {
        Message<AnimeSearchRequest> message = MessageBuilder
            .withPayload(data)
            .setHeader(KafkaHeaders.TOPIC, apiRequestTopicName)
            .build();

        log.info("Sending message: {}", data);
        kafkaTemplate.send(message);
    }

    @Override
    public void sendCompressedAnimeList(List<CompressedAnimeDto> data) {
        Message<List<CompressedAnimeDto>> message = MessageBuilder
            .withPayload(data)
            .setHeader(KafkaHeaders.TOPIC, animeListTopicName)
            .build();

        log.info("Sending message: {}", data);
        kafkaTemplate.send(message);
    }

    @Override
    public void sendFullSingleAnime(AnimeDto data) {
        Message<AnimeDto> message = MessageBuilder
            .withPayload(data)
            .setHeader(KafkaHeaders.TOPIC, singleAnimeTopicName)
            .build();

        log.info("Sending message: {}", data);
        kafkaTemplate.send(message);
    }

}
