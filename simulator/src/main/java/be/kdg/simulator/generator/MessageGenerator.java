package be.kdg.simulator.generator;

import be.kdg.simulator.model.CameraMessage;

/**
 * Defines what generator should be able to do.
 * One generator will generate messages at random, the other based on a file.
 */
public interface MessageGenerator {

    CameraMessage generate();
}
