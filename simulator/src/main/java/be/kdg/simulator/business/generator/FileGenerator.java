package be.kdg.simulator.business.generator;

import be.kdg.simulator.domain.CameraMessage;
import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * This mode will be used to simulate traffic violations.
 * The messages that will be sent won't be generated at random but will be read from a text file at a custom location.
 * In this file, each message item will be separated by a comma (cameraID, licensePlate, delay).
 * The delay (in ms) determines how long we should wait after each message.
 */
public class FileGenerator implements MessageGenerator {

    // Tried to inject these two, gave me a bunch of issues (including with tests)
    private CSVReader csvReader;
    private Reader reader;

    FileGenerator(String path) throws IOException {
        reader = Files.newBufferedReader(Paths.get(path));
        csvReader = new CSVReader(reader);
    }

    @Override
    public Optional<CameraMessage> generate() throws IOException {

        return readLine();
    }

    private Optional<CameraMessage> readLine() throws IOException {

        String[] row = csvReader.readNext();

        if (row == null || row.length == 0) {
            csvReader.close();
            reader.close();
            return Optional.empty();
        }

        CameraMessage message = new CameraMessage(Integer.parseInt(row[0]), row[1], LocalDateTime.now());
        message.setDelay(Integer.parseInt(row[2]));

        return Optional.of(message);
    }
}
