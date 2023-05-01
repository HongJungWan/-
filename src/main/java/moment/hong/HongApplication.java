package moment.hong;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableBatchProcessing
@EnableJpaAuditing
@SpringBootApplication
public class HongApplication {
    public static void main(String[] args) {
        SpringApplication.run(HongApplication.class, args);
    }
}
