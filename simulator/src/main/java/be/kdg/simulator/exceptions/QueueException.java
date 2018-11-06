package be.kdg.simulator.exceptions;

public class QueueException extends Exception {

    public QueueException(String message) {
        super(message);
    }

    public QueueException(Throwable cause) {
        super(cause);
    }

    public QueueException(String message, Throwable cause) {
        super(message, cause);
    }
}
