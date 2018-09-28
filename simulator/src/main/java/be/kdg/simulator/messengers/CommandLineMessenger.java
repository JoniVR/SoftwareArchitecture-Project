package be.kdg.simulator.messengers;
import be.kdg.simulator.generators.MessageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "messenger.type", havingValue = "cmd")
public class CommandLineMessenger implements Messenger {

    @Autowired
    private MessageGenerator messageGenerator;

    @Override
    public void sendMessage() {
        System.out.println(messageGenerator.generate());
    }
}
