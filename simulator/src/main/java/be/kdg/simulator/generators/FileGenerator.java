package be.kdg.simulator.generators;

import be.kdg.simulator.models.CameraMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Deze modus wordt gebruikt voor het simuleren van overtredingen.
 * Door te sturen berichten worden niet gegenereerd maar uit een tekstbestand gelezen dat op een instelbare locatie staat.
 * Hierin staat per bericht een door komma’s gescheiden lijn met (cameraID, nummerplaat, delay).
 * De delay (in millis) bepaalt hoe lang er na het vorige bericht moet gewacht worden.
 *
 * @Author Joni Van Roost
 * @Version 0.0.1
 */
@Component
@ConditionalOnProperty(name = "generator.type", havingValue = "file")
public class FileGenerator implements MessageGenerator {

    @Override
    public CameraMessage generate() {
        return null;
    }
}
