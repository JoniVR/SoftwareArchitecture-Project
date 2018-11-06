package be.kdg.simulator.business.messenger;

import be.kdg.simulator.business.domain.CameraMessage;
import be.kdg.simulator.exceptions.ObjectMappingException;
import be.kdg.simulator.exceptions.QueueException;
import org.springframework.amqp.AmqpException;

import java.io.IOException;

/**
 * Defines what messenger should be able to do.
 * One messenger will send messages to the console, the other one will send messages to a queue.
 */
public interface Messenger {

    void sendMessage(CameraMessage cameraMessage) throws ObjectMappingException, QueueException;
}
