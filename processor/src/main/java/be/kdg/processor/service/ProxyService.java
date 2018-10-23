package be.kdg.processor.service;

import be.kdg.processor.domain.camera.Camera;
import be.kdg.processor.domain.vehicle.Vehicle;
import be.kdg.processor.exceptions.ObjectMappingException;
import be.kdg.processor.util.JSONMapperService;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.CameraServiceProxy;
import be.kdg.sa.services.LicensePlateNotFoundException;
import be.kdg.sa.services.LicensePlateServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * This class is responsible for reaching out
 * to external services and adapting their results to the correct objects.
 */
public class ProxyService {

    @Autowired
    private LicensePlateServiceProxy licensePlateServiceProxy;

    @Autowired
    private CameraServiceProxy cameraServiceProxy;

    @Autowired
    private JSONMapperService jsonMapperService;

    public Camera getCameraObject(int id) throws IOException, ObjectMappingException, CameraNotFoundException {

        return jsonMapperService.convertJSONStringToCameraObject(cameraServiceProxy.get(id));
    }

    public Vehicle getVehicleObject(String licensePlate) throws IOException, ObjectMappingException, LicensePlateNotFoundException {

        return jsonMapperService.convertJSONStringToVehicleObject(licensePlateServiceProxy.get(licensePlate));
    }
}
