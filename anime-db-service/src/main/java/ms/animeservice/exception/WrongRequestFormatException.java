package ms.animeservice.exception;

public class WrongRequestFormatException extends RuntimeException{
    public WrongRequestFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
