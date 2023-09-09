package ms.animedbservice.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.animedbservice.dto.AnimeDto;
import ms.animedbservice.model.AnimeSearchRequest;
import ms.animedbservice.service.KafkaService;
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

    private final List<String> responseTopicName;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendMessage(AnimeSearchRequest data) {
        Message<AnimeSearchRequest> message = MessageBuilder
            .withPayload(data)
            .setHeader(KafkaHeaders.TOPIC, responseTopicName.get(0))
            .build();

        log.info("Sending message: {}", data);
        kafkaTemplate.send(message);
    }

    @Override
    public void sendMessage(List<AnimeDto> data) {
        Message<List<AnimeDto>> message = MessageBuilder
            .withPayload(data)
            .setHeader(KafkaHeaders.TOPIC, responseTopicName.get(1))
            .build();

        log.info("Sending message: {}", data);
        kafkaTemplate.send(message);
    }
}
