package be.kdg.processor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"job.autorun.enabled=false"})
public class ProcessorApplicationTests {

	@Test
	public void contextLoads() {


	}
}
