package ms.animedbservice.exception;

public class WrongRequestFormatException extends RuntimeException{
    public WrongRequestFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
