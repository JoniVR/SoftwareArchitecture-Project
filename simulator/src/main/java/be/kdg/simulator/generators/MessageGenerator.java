package be.kdg.simulator.generators;

import be.kdg.simulator.model.CameraMessage;

/**
 * Definieert wat de generators moeten genereren.
 */
public interface MessageGenerator {

    CameraMessage generate();
}
