package be.kdg.processor.exceptions;

public class UserException extends Throwable {

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }
}
