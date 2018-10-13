package be.kdg.processor.processing;

import be.kdg.processor.config.RabbitConfig;
import be.kdg.processor.exceptions.ObjectMappingException;
import be.kdg.processor.mapping.JSONMapperService;
import be.kdg.processor.mapping.XMLMapperService;
import be.kdg.processor.model.camera.Camera;
import be.kdg.processor.model.camera.CameraMessage;
import be.kdg.processor.model.vehicle.Vehicle;
import be.kdg.processor.service.ProxyService;
import be.kdg.processor.violation.ViolationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Responsible for processing/handling of messages from the RabbitMq queue.
 * Receives the cameraMessage and notifies & passes it to the MessageHandler.
 *
 * @Author Joni Van Roost
 */
public class Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class);

    @Autowired
    private XMLMapperService xmlMapperService;

    @Autowired
    private JSONMapperService jsonMapperService;

    @Autowired
    private Collection<ViolationStrategy> listeners = new ArrayList<>();

    @Autowired
    private ProxyService proxyService;

    //TODO: handle exceptions correctly

    @RabbitListener(queues = RabbitConfig.QUEUE_SPECIFIC_NAME)
    public void receiveMessage(final String cameraMessageString) {

        try {

            CameraMessage cameraMessage = xmlMapperService.convertXmlStringToCameraMessage(cameraMessageString);
            LOGGER.info("Received CameraMessage: {}", cameraMessage);
            processMessage(cameraMessage);

        } catch (ObjectMappingException e) {

            LOGGER.warn("Probleem met het mappen van het object.", e);

        }
    }

    /**
     * In here we process the received cameraMessage.
     * The reason we process it here instead of inside the respective listeners is for extendability reasons.
     * By making the implementation more abstract, we can easily add new ViolationStrategies without much work
     * since talking to the ProxyService already happens here.
     * Processing on this level also makes caching results from the ProxyService easier.
     *
     * @param cameraMessage The incoming CameraMessage from RabbitMQ.
     */
    private void processMessage(CameraMessage cameraMessage) {

        int camId = cameraMessage.getId();
        String licensePlate = cameraMessage.getLicenseplate();

        try {

            Camera camera = jsonMapperService.convertJSONStringToCameraObject(proxyService.cameraServiceProxy().get(camId));
            LOGGER.info("Received Camera info from ProxyService: {}", camera);
            Vehicle vehicle = jsonMapperService.convertJSONStringToVehicle(proxyService.licensePlateServiceProxy().get(licensePlate));
            LOGGER.info("Received Vehicle info from ProxyService: {}", vehicle);

            notifyListeners(camera, vehicle);

        } catch (ObjectMappingException e) {

            LOGGER.warn("Probleem met het mappen van het object.", e);

        } catch (IOException e) {

            LOGGER.warn("Probleem met één van de ProxyServices.", e);

        }
    }

    private void notifyListeners(Camera camera, Vehicle vehicle){
        listeners.forEach(listener -> listener.detect(camera, vehicle));
    }
}
