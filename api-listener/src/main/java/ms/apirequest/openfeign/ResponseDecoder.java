package ms.apirequest.openfeign;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import ms.apirequest.exception.ApiErrorResponseException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ResponseDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("Api response error. Status code: {}, reason: {}", response.status(), response.reason());
        return new ApiErrorResponseException("Api response contains error in method" + methodKey);
    }
}
