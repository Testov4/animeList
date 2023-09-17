package ms.animeservice.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.animeservice.model.dto.AnimeDto;
import ms.animeservice.payload.AnimeSearchRequest;
import ms.animeservice.service.KafkaService;
import ms.animeservice.model.dto.PartialAnimeDto;
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
    private String foundAnimeListTopicName;

    @Value("${spring.kafka.topic.name.produce-anime-user-list}")
    private String userAnimeListTopicName;

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
    public void sendFoundAnimeList(List<PartialAnimeDto> data) {
        Message<List<PartialAnimeDto>> message = MessageBuilder
            .withPayload(data)
            .setHeader(KafkaHeaders.TOPIC, foundAnimeListTopicName)
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

    @Override
    public void sendUserAnimeList(List<PartialAnimeDto> data) {
        Message<List<PartialAnimeDto>> message = MessageBuilder
            .withPayload(data)
            .setHeader(KafkaHeaders.TOPIC, userAnimeListTopicName)
            .build();

        log.info("Sending message: {}", data);
        kafkaTemplate.send(message);
    }

}
