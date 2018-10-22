package be.kdg.simulator.business.generator;

import be.kdg.simulator.domain.CameraMessage;

import java.util.Optional;

/**
 * Defines what generator should be able to do.
 * One generator will generate messages at random, the other based on a file.
 */
public interface MessageGenerator {

    Optional<CameraMessage> generate();
}
