package moment.hong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HongApplication {
	public static void main(String[] args) {
		SpringApplication.run(HongApplication.class, args);
	}
}
