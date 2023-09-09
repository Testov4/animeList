package ms.apirequest.openfeign;

import ms.apirequest.model.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@FeignClient(value = "anime-request", url = "${api.url}")
public interface AnimeRequestClient {

    @GetMapping
    ApiResponse searchAnime(@RequestParam Map<String, Object> params);
}
