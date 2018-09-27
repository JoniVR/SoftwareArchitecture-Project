package be.kdg.simulator;

import be.kdg.simulator.util.MessageScheduleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimulatorApplication.class, args);

		MessageScheduleService messageScheduleService;
		messageScheduleService = new MessageScheduleService();
		messageScheduleService.run();
	}
}
