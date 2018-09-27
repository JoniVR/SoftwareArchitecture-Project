package be.kdg.simulator.generators;

import be.kdg.simulator.model.CameraMessage;

/**
 * Defineert wat de generators moeten kunnen.
 * De ene generator zal random genereren, de andere op basis van een file.
 */
public interface MessageGenerator {

    CameraMessage generate();
}
