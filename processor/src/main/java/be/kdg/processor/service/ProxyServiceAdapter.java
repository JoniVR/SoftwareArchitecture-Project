package be.kdg.processor.service;

import be.kdg.processor.exceptions.ObjectMappingException;
import be.kdg.processor.mapping.JSONMapperService;
import be.kdg.processor.model.camera.Camera;
import be.kdg.processor.model.vehicle.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * This class is responsible for reaching out
 * to external services and adapting their results to the correct objects.
 */
public class ProxyServiceAdapter {

    @Autowired
    private ProxyService proxyService;

    @Autowired
    private JSONMapperService jsonMapperService;

    public Camera getCameraObject(int id) throws IOException, ObjectMappingException {

        return jsonMapperService.convertJSONStringToCameraObject(proxyService.cameraServiceProxy().get(id));
    }

    public Vehicle getVehicleObject(String licensePlate) throws IOException, ObjectMappingException {

        return jsonMapperService.convertJSONStringToVehicleObject(proxyService.licensePlateServiceProxy().get(licensePlate));
    }
}
