package be.kdg.simulator.business.generator;

import be.kdg.simulator.business.domain.CameraMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;

/**
 * Generates messages at random for load testing.
 */
public class RandomMessageGenerator implements MessageGenerator {

    @Getter
    @Setter
    private int maxCameraId = 5;

    @Value("${busy.hours}")
    private String busyHours;

    @Override
    public Optional<CameraMessage> generate() {

        return Optional.of(CameraMessage
                .builder()
                .id(generateCameraId())
                .licenseplate(generateLicensePlate())
                .timestamp(LocalDateTime.now())
                .delay(generateDelay())
                .build());
    }

    private int generateDelay() {

        DateTimeFormatter timeParser = DateTimeFormatter.ofPattern("HHmm");
        LocalTime currentTime = LocalTime.now();

        // get the timeSlots
        String[] timeSlots = busyHours.split(",");

        // get the hours and their respective delay for each slot
        for (String timeSlot : timeSlots) {

            String[] times = timeSlot.split(":");
            LocalTime startTime = LocalTime.parse(times[0], timeParser);
            LocalTime endTime = LocalTime.parse(times[1], timeParser);
            int delay = Integer.parseInt(times[2]);

            // check if current time is in the busy hours
            if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {
                return delay;
            }
        }
        // return a default delay of 1000 if not between busy hours
        return 1000;
    }

    // private functions
    private String generateLicensePlate() {

        return String.format("%s-%s-%s", generateDigits(1), generateLetters(3), generateDigits(3));
    }

    private int generateCameraId() {

        Random r = new Random();
        int lower = 1;
        int upper = this.maxCameraId + 1;

        return r.nextInt(upper - lower) + lower;
    }

    private String generateLetters(int amount) {

        StringBuilder letters = new StringBuilder();
        int n = 'Z' - 'A' + 1;

        for (int i = 0; i < amount; i++) {
            char c = (char) ('A' + Math.random() * n);
            letters.append(c);
        }
        return letters.toString();
    }

    private String generateDigits(int amount) {

        StringBuilder digits = new StringBuilder();
        int n = '9' - '0' + 1;

        for (int i = 0; i < amount; i++) {
            char c = (char) ('0' + Math.random() * n);
            digits.append(c);
        }
        return digits.toString();
    }
}
