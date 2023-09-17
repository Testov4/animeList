package ms.apirequest.service.implementation;

import ms.apirequest.model.Anime;
import ms.apirequest.service.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final String responseTopicName;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendMessage(List<Anime> data) {
        Message<List<Anime>> message = MessageBuilder
            .withPayload(data)
            .setHeader(KafkaHeaders.TOPIC, responseTopicName)
            .build();

        log.info("Sending message: {}", data);
        kafkaTemplate.send(message);
    }

}
