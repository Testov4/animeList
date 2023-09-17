package ms.apirequest.service;

import ms.apirequest.model.Anime;
import java.util.List;

public interface KafkaService {

    void sendMessage(List<Anime> data);

}
