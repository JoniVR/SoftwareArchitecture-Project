package be.kdg.simulator.messengers;

/**
 * Defines what messengers should be able to do.
 * One messenger will send messages to the console, the other one will send messages to a queue.
 */
public interface Messenger {

    void sendMessage();
}
