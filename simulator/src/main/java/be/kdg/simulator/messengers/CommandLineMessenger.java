package be.kdg.simulator.messengers;
import be.kdg.simulator.generators.MessageGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CommandLineMessenger implements Messenger {

    private final MessageGenerator messageGenerator;

    // Constructor injection (geen autowired meer nodig hier)
    public CommandLineMessenger(@Qualifier("randomMessageGenerator") MessageGenerator messageGenerator) {
        this.messageGenerator = messageGenerator;
    }

    @Override
    public void sendMessage() {
        System.out.println(messageGenerator.generate());
    }
}
