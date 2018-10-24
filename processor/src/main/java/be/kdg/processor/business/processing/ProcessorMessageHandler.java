package be.kdg.processor.business.processing;

import be.kdg.processor.business.violation.ViolationStrategy;
import be.kdg.processor.domain.camera.Camera;
import be.kdg.processor.domain.camera.CameraMessage;
import be.kdg.processor.domain.fine.Fine;
import be.kdg.processor.domain.vehicle.Vehicle;
import be.kdg.processor.exceptions.ObjectMappingException;
import be.kdg.processor.service.FineService;
import be.kdg.processor.service.ProxyService;
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
    private ProxyService proxyService;

    /**
     * In here we process the received cameraMessage.
     * The reason we process it here instead of inside the respective listeners is for extendability reasons.
     * By making the implementation more abstract, we can easily add new ViolationStrategies without much work
     * since talking to the ProxyService already happens here.
     * Processing on this level also makes caching results from the ProxyService easier and reduces overhead (only call proxyservices once).
     *
     * @param cameraMessage The incoming CameraMessage from RabbitMQ.
     * @throws IOException Will be thrown when the proxyservice has a problem.
     * @throws ObjectMappingException Will be thrown when the queue message can't be mapped from an object.
     */
    public void processMessage(CameraMessage cameraMessage) throws IOException, ObjectMappingException {

        int camId = cameraMessage.getId();
        String licensePlate = cameraMessage.getLicenseplate();

        Camera camera = proxyService.getCameraObject(camId);
        LOGGER.info("Received Camera info from ProxyService: {}", camera);
        Vehicle vehicle = proxyService.getVehicleObject(licensePlate);
        LOGGER.info("Received Vehicle info from ProxyService: {}", vehicle);

        notifyListeners(camera, vehicle);
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