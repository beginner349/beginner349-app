package sg.com.chen.Beginner349.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDate;

@ConfigurationProperties(prefix = "custom-properties")
public record ApplicationProperties(boolean exitOnErrors, LocalDate tradeStartDate) {

}
