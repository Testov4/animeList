package ms.apirequest.exception;

public class WrongRequestFormatException extends RuntimeException{
    public WrongRequestFormatException(String message) {
        super(message);
    }
}
