package be.kdg.processor.processing;

import be.kdg.processor.exceptions.ObjectMappingException;
import be.kdg.processor.model.camera.Camera;
import be.kdg.processor.model.camera.CameraMessage;
import be.kdg.processor.model.fine.Fine;
import be.kdg.processor.model.vehicle.Vehicle;
import be.kdg.processor.service.FineService;
import be.kdg.processor.service.ProxyServiceAdapter;
import be.kdg.processor.violation.ViolationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Responsible for handling a detected cameraMessage once processed.
 */
public class ProcessorMessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessorMessageHandler.class);

    @Autowired
    private FineService fineService;

    @Autowired
    private Collection<ViolationStrategy> listeners = new ArrayList<>();

    @Autowired
    private ProxyServiceAdapter proxyServiceAdapter;

    /**
     * In here we process the received cameraMessage.
     * The reason we process it here instead of inside the respective listeners is for extendability reasons.
     * By making the implementation more abstract, we can easily add new ViolationStrategies without much work
     * since talking to the ProxyService already happens here.
     * Processing on this level also makes caching results from the ProxyService easier and reduces overhead (only call proxyservices once).
     *
     * @param cameraMessage The incoming CameraMessage from RabbitMQ.
     */
    public void processMessage(CameraMessage cameraMessage) {

        int camId = cameraMessage.getId();
        String licensePlate = cameraMessage.getLicenseplate();

        try {

            Camera camera = proxyServiceAdapter.getCameraObject(camId);
            LOGGER.info("Received Camera info from ProxyService: {}", camera);
            Vehicle vehicle = proxyServiceAdapter.getVehicleObject(licensePlate);
            LOGGER.info("Received Vehicle info from ProxyService: {}", vehicle);

            notifyListeners(camera, vehicle);

        } catch (ObjectMappingException e) {

            LOGGER.warn("Probleem met het mappen van het object.", e);

        } catch (IOException e) {

            LOGGER.warn("Probleem met één van de ProxyServices.", e);

        }
    }

    private void notifyListeners(Camera camera, Vehicle vehicle) {

        for (ViolationStrategy listener : listeners) {
            boolean violationDetected = listener.detect(camera, vehicle);
            if (violationDetected) handleViolation(listener, camera, vehicle);
        }
    }

    private void handleViolation(ViolationStrategy strategy, Camera camera, Vehicle vehicle) {

        Fine fine = strategy.calculateFine(camera, vehicle);

        // save fine
        fineService.save(fine);
        LOGGER.info("Created fine and saved it to the database: {}", fine);
    }
}
