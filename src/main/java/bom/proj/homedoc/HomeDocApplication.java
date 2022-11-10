package bom.proj.homedoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class HomeDocApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeDocApplication.class, args);
	}

}
