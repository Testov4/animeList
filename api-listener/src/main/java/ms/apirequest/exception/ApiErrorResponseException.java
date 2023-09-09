package ms.apirequest.exception;

public class ApiErrorResponseException extends RuntimeException{

    public ApiErrorResponseException(String message) {
        super(message);
    }

}
