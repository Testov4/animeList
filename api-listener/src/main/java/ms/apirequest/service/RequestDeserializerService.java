package ms.apirequest.service;

import java.util.Map;

public interface RequestDeserializerService {

    Map<String, Object> deserializeAnimeSearchRequest(String json);
}
