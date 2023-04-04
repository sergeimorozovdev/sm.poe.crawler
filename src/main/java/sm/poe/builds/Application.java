package sm.poe.builds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
@EnableScheduling
public class Application {

	public static void main(String[] args)
			throws URISyntaxException, IOException, InterruptedException
	{
		SpringApplication.run(Application.class, args);
		
	}

}
