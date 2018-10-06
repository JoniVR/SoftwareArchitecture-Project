package be.kdg.simulator.messenger;

import be.kdg.simulator.model.CameraMessage;

/**
 * Defines what messenger should be able to do.
 * One messenger will send messages to the console, the other one will send messages to a queue.
 */
public interface Messenger {

    void sendMessage(CameraMessage cameraMessage);
}
