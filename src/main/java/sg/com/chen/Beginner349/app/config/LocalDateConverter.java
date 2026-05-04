package sg.com.chen.Beginner349.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Custom Converter to support converting a property to a specific class type.
 *
 * @tag @ConfigurationPropertiesBinding - to register our custom Converter:
 *
 */

@Component
@ConfigurationPropertiesBinding
public class LocalDateConverter implements Converter<String, LocalDate> {
    Logger logger = LoggerFactory.getLogger(LocalDateConverter.class);

    @Override
    public LocalDate convert(String source) {
        LocalDate date = LocalDate.parse(source, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        logger.info("[LocalDateConverter.java] converting to {}", date.getClass());
        return LocalDate.parse(source, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
