package sg.com.chen.Beginner349.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("sg.com.chen.Beginner349.app.config")
public class Beginner349AppApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Beginner349AppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
