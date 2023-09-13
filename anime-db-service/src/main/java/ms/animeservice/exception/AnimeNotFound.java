package ms.animeservice.exception;

public class AnimeNotFound extends RuntimeException{
    public AnimeNotFound(String message) {
        super(message);
    }
}
