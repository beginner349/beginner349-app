package sg.com.chen.Beginner349Application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDate;

@ConfigurationProperties(prefix = "app")
public record ApplicationProperties(boolean exitOnErrors, LocalDate tradeStartDate) {

}
