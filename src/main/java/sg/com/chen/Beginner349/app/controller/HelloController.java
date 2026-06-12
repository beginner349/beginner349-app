package sg.com.chen.Beginner349.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.com.chen.Beginner349.app.aop.LogExecutionTime;
import sg.com.chen.Beginner349.app.config.ApplicationProperties;

import java.net.URI;

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

    // This endpoint demonstrates how to return a redirect response to the client
    @GetMapping("/redirect")
    public ResponseEntity<Void> redirect() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("https://www.google.com"));

        // Returns a 302 Found status code with the Location header set to the target URL
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
