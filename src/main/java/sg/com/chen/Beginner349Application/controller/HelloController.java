package sg.com.chen.Beginner349Application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.com.chen.Beginner349Application.aop.LogExecutionTime;
import sg.com.chen.Beginner349Application.config.ApplicationProperties;

@RestController
public class HelloController {
    Logger logger = LoggerFactory.getLogger(HelloController.class);
    private final ApplicationProperties applicationProperties;

    public HelloController(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @GetMapping("/")
    @LogExecutionTime
    public String hello() {
        logger.info("exitOnErrors: {}, tradeStartDate: {}", applicationProperties.exitOnErrors(), applicationProperties.tradeStartDate());
        return "Welcome to Jiajun's SpringBoot project";
    }
    
    // This endpoint is protected and requires authentication
    @GetMapping("/secured")
    public String securedEndpoint() {
        return "This is a secured endpoint, only authenticated users can access this";
    }
}
