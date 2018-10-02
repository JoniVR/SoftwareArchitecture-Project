package be.kdg.simulator.generators;

import be.kdg.simulator.models.CameraMessage;

/**
 * Defines what generators should be able to do.
 * One generator will generate messages at random, the other based on a file.
 */
public interface MessageGenerator {

    CameraMessage generate();
}
