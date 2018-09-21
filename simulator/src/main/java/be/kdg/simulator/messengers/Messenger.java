package be.kdg.simulator.messengers;

import be.kdg.simulator.model.CameraMessage;

/**
 * Defineert wat de messenger moet kunnen.
 * De ene messenger stuurt naar console, andere messenger stuurt naar queue.
 */
public interface Messenger {

    void sendMessage();
}
