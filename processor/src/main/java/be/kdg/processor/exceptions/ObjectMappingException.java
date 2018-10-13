package be.kdg.processor.exceptions;

public class ObjectMappingException extends Exception {

    public ObjectMappingException(String message) {
        super(message);
    }

    public ObjectMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
