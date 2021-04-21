package hose.boardrestapi.common.custom_exception;


public class PostNotFound extends RuntimeException {

    public PostNotFound(String message) {
        super(message);
    }
}
