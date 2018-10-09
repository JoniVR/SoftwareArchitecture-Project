package be.kdg.simulator.generator;

import be.kdg.simulator.model.CameraMessage;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * This mode will be used to simulate traffic violations.
 * The messages that will be sent won't be generated at random but will be read from a text file at a custom location.
 * In this file, each message item will be separated by a comma (cameraID, licensePlate, delay).
 * The delay (in ms) determines how long we should wait after each message.
 *
 * @Author Joni Van Roost
 * @Version 0.0.1
 */
public class FileGenerator implements MessageGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileGenerator.class);

    private final String csvPath;
    private Iterator it;

    public FileGenerator(String csvPath) {

        this.csvPath = csvPath;

        try {
            ArrayList<CameraMessage> cameraMessages = generateAllMessages();
            it = cameraMessages.iterator();
        } catch (NoSuchFileException ex){
            LOGGER.warn("No file found.");
        } catch (IOException ex) {
            LOGGER.warn("No messages could be produced from CSV file.");
        } catch (Exception ex){
            LOGGER.warn("Something went wrong trying to generate messages from a file.", ex);
        }
    }

    @Override
    public Optional<CameraMessage> generate() {

        return Optional.of((CameraMessage) it.next());
    }

    private ArrayList<CameraMessage> generateAllMessages() throws IOException {
        ArrayList<CameraMessage> messages = new ArrayList<>();

        Reader reader = Files.newBufferedReader(Paths.get(csvPath));
        CSVReader csvReader = new CSVReaderBuilder(reader).build();
        List<String[]> allData = csvReader.readAll();

        for (String[] row : allData) {
            CameraMessage message = new CameraMessage(Integer.parseInt(row[0]), row[1], LocalDateTime.now());

            message.setDelay(Integer.parseInt(row[2]));
            messages.add(message);
        }
        return messages;
    }
}
