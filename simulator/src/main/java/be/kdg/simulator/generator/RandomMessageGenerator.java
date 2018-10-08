package be.kdg.simulator.generator;

import be.kdg.simulator.model.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * Generates messages at random for load testing.
 */
public class RandomMessageGenerator implements MessageGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomMessageGenerator.class);
    private int maxCameraId = 3;

    @Override
    public CameraMessage generate() {

        return new CameraMessage(generateCameraId(), generateLicensePlate(), LocalDateTime.now());
    }

    public void setMaxCameraId(int maxCameraId) {
        this.maxCameraId = maxCameraId;
    }

    public int getMaxCameraId() {
        return maxCameraId;
    }

    // private functions
    private String generateLicensePlate(){

        return String.format("%s-%s-%s", generateDigits(1), generateLetters(3), generateDigits(3));
    }

    private int generateCameraId(){

        Random r = new Random();
        int lower = 1;
        int upper = this.maxCameraId + 1;
        int result = r.nextInt(upper-lower) + lower;

        return result;
    }

    private String generateLetters(int amount) {

        String letters = "";
        int n = 'Z' - 'A' + 1;

        for (int i = 0; i < amount; i++) {
            char c = (char) ('A' + Math.random() * n);
            letters += c;
        }
        return letters;
    }

    private String generateDigits(int amount) {

        String digits = "";
        int n = '9' - '0' + 1;

        for (int i = 0; i < amount; i++) {
            char c = (char) ('0' + Math.random() * n);
            digits += c;
        }
        return digits;
    }
}