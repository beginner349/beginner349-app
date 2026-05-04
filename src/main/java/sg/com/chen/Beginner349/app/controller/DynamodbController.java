package sg.com.chen.Beginner349.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;

import java.util.List;

@RestController
@RequestMapping("/dynamodb")
public class DynamodbController {
    private final DynamoDbClient dynamoDbClient;

    public DynamodbController(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    @GetMapping("/list")
    public List<String> listTable() {
        ListTablesResponse listTablesResponse = dynamoDbClient.listTables();
        return listTablesResponse.tableNames();
    }
}
