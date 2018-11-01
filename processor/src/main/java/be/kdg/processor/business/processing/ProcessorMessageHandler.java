package be.kdg.processor.business.processing;

import be.kdg.processor.business.domain.camera.Camera;
import be.kdg.processor.business.domain.camera.CameraMessage;
import be.kdg.processor.business.domain.camera.ProcessedCameraMessage;
import be.kdg.processor.business.domain.fine.Fine;
import be.kdg.processor.business.domain.vehicle.Vehicle;
import be.kdg.processor.business.domain.violation.Violation;
import be.kdg.processor.business.service.FineService;
import be.kdg.processor.business.service.ProxyService;
import be.kdg.processor.business.service.ViolationService;
import be.kdg.processor.business.violation.ViolationStrategy;
import be.kdg.processor.exceptions.ObjectMappingException;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.InvalidLicensePlateException;
import be.kdg.sa.services.LicensePlateNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

/**
 * Responsible for handling a detected cameraMessage once processed.
 */
public class ProcessorMessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessorMessageHandler.class);

    @Autowired
    private FineService fineService;

    @Autowired
    private ViolationService violationService;

    @Autowired
    private Collection<ViolationStrategy> listeners;

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
     */
    public void processMessage(CameraMessage cameraMessage) throws ObjectMappingException, IOException, CameraNotFoundException, InvalidLicensePlateException, LicensePlateNotFoundException {

        int camId = cameraMessage.getId();
        String licensePlate = cameraMessage.getLicenseplate();

        Camera camera = proxyService.getCameraObject(camId);
        Vehicle vehicle = proxyService.getVehicleObject(licensePlate);
        LOGGER.info("Received: Camera from proxyservice: {}", camera);
        LOGGER.info("Received: Vehicle from proxyservice: {}", vehicle);

        ProcessedCameraMessage processedCameraMessage = new ProcessedCameraMessage(vehicle, camera, cameraMessage.getTimestamp());
        LOGGER.info("Generated: ProcessedCameraMessage: CameraId: {}, VehiclePlate: {}, Timestamp: {}", camera.getId(), vehicle.getPlateId(), processedCameraMessage.getTimeStamp());

        notifyListeners(processedCameraMessage);
    }

    private void notifyListeners(ProcessedCameraMessage processedCameraMessage) {

        for (ViolationStrategy listener : listeners) {

            Optional<Violation> violationOptional = listener.detect(processedCameraMessage);

            violationOptional.ifPresent(violation -> {

                LOGGER.info("Detected: {} for plate: {}, NN: {}, euroNr: {}", listener.getClass().getSimpleName(), violation.getLicensePlate(), violation.getEuroNorm());
                violationService.addViolation(violation);
                LOGGER.info("Created: Violation and saved it to the database {}", violation);
                handleViolation(listener, violation);
            });
        }
    }

    private void handleViolation(ViolationStrategy strategy, Violation violation) {

        Fine fine = strategy.calculateFine(violation);

        // save fine
        fineService.save(fine);
        LOGGER.info("Created: Fine and saved it to the database: {}", fine);
    }
}