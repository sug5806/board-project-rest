package hose.boardrestapi.util.custom_exception;


public class PostNotFound extends RuntimeException {

    public PostNotFound(String message) {
        super(message);
    }
}
