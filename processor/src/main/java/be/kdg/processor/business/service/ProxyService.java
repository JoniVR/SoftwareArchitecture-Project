package be.kdg.processor.business.service;

import be.kdg.processor.business.domain.camera.Camera;
import be.kdg.processor.business.domain.vehicle.Vehicle;
import be.kdg.processor.exceptions.ObjectMappingException;
import be.kdg.processor.util.JSONMapperService;
import be.kdg.sa.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

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

    @Cacheable("cameras")
    public Camera getCameraObject(int id) throws IOException, ObjectMappingException, CameraNotFoundException {

        return jsonMapperService.convertJSONStringToCameraObject(cameraServiceProxy.get(id));
    }

    @Cacheable("vehicles")
    public Vehicle getVehicleObject(String licensePlate) throws IOException, ObjectMappingException, LicensePlateNotFoundException, InvalidLicensePlateException {

        return jsonMapperService.convertJSONStringToVehicleObject(licensePlateServiceProxy.get(licensePlate));
    }
}
