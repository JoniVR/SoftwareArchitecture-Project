package be.kdg.simulator;

import be.kdg.simulator.services.MessageSchedulerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimulatorApplication.class, args);

		MessageSchedulerService messageScheduleService;
		messageScheduleService = new MessageSchedulerService();
		messageScheduleService.run();
	}
}
