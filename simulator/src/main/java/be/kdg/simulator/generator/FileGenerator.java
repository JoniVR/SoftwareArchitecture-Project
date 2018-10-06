package be.kdg.simulator.generator;

import be.kdg.simulator.model.CameraMessage;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This mode will be used to simulate traffic violations.
 * The messages that will be sent won't be generated at random but will be read from a text file at a custom location.
 * In this file, each message item will be separated by a comma (cameraID, licensePlate, delay).
 * The delay (in ms) determines how long we should wait after each message.
 *
 * @Author Joni Van Roost
 * @Version 0.0.1
 */
@Component
@ConditionalOnProperty(name = "generator.type", havingValue = "file")
public class FileGenerator implements MessageGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileGenerator.class);

    private final String csvPath;
    private Iterator it;

    //TODO: fix "Error creating bean with name 'fileGenerator'" error.
    FileGenerator(String csvPath) {
        this.csvPath = csvPath;
        try {
            ArrayList<CameraMessage> cameraMessages = generateAllMessages();
            it = cameraMessages.iterator();
        } catch (IOException e) {
            LOGGER.warn("No messages could be produced from CSV file.");
            e.printStackTrace();
        }
    }

    @Override
    public CameraMessage generate() {
        while(it.hasNext()) return (CameraMessage) it.next();
        System.exit(0);
        return null;
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
