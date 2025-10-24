package sg.com.chen.Beginner349Application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("sg.com.chen.Beginner349Application.config")
public class Beginner349Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Beginner349Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
