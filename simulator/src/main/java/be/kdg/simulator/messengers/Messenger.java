package be.kdg.simulator.messengers;

/**
 * Defineert wat de messengers moeten kunnen.
 * De ene messenger stuurt naar console, andere messenger stuurt naar queue.
 */
public interface Messenger {

    void sendMessage();
}
