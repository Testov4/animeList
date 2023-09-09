package ms.apirequest.kafka;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = "${spring.kafka.topic.name.consume}", brokerProperties = "auto.create.topics.enable=false")
public class AnimeSearchConsumerTest {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    public void testAnimeSearchConsumer(CapturedOutput output) throws Exception {
        Map<String, Object> testMessage = Map.of("q", "test_title", "genres", "");
        kafkaTemplate.send("anime-api-request", testMessage);

        Thread.sleep(4000);

        assertThat(output)
            .contains("List from API received: []", "Sending message: []");
        assertDoesNotThrow(() -> {});
    }
}
