package sg.com.chen.Beginner349.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@SpringBootTest
class Beginner349AppApplicationTests {
    @MockitoBean
    private DynamoDbClient dynamoDbClient;

    @Test
    void contextLoads() {
        Assertions.assertTrue(true, "This assertion should always be true.");
    }
}
