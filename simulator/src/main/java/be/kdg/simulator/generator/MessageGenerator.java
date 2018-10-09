package be.kdg.simulator.generator;

import be.kdg.simulator.model.CameraMessage;

import java.util.Optional;

/**
 * Defines what generator should be able to do.
 * One generator will generate messages at random, the other based on a file.
 */
public interface MessageGenerator {

    Optional<CameraMessage> generate();
}
