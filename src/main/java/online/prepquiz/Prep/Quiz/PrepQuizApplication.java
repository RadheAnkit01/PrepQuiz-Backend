package online.prepquiz.Prep.Quiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PrepQuizApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrepQuizApplication.class, args);
	}

}
