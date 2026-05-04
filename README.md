# Beginner349 App

[![Java CI with Maven](https://github.com/beginner349/beginner349-app/actions/workflows/maven.yml/badge.svg)](https://github.com/beginner349/beginner349-app/actions/workflows/maven.yml)

A comprehensive Spring Boot 4.0 application demonstrating cloud-native Java development patterns, featuring AWS DynamoDB support, and security best practices.

## Features

- **Spring Boot 4.0.1** with Java 25
- **Security**: OAuth2 Resource Server / Spring Security
- **Data Persistence**: AWS DynamoDB support
- **AOP**: Aspect-Oriented Programming with execution time logging
- **REST API**: Well-structured controllers with DynamoDB and general endpoints
- **Monitoring**: Spring Boot Actuator for application health and metrics

## Prerequisites

- **Java 25** or higher
- **Maven 3.9.12** or higher
- **Docker** (for containerized setup)

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd beginner349-app
```

### 2. Local Development Setup

#### Without Docker

```bash
# Build the project
mvn clean package

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`


### 3. Application Configuration

The application uses environment-based configuration:

- **application.yml** - Default configuration

## Project Structure

```
src/main/java/sg/com/chen/Beginner349/app/
├── controller/          # REST API endpoints
│   ├── HelloController
│   └── DynamodbController
├── config/              # Configuration classes
│   ├── AppConfig
│   ├── SecurityConfig
│   └── ApplicationProperties
├── aop/                 # Aspect-Oriented Programming
│   ├── ExampleAspect
│   └── LogExecutionTime

```

## API Endpoints

### Health & Actuator

```
GET  /actuator/health          - Application health status
GET  /actuator/metrics         - Application metrics
```

### Application Endpoints

```
GET  /hello                    - Basic greeting endpoint
GET  /api/dynamodb/*           - DynamoDB operations
```

## Building Docker Image for ECR

To package and push to AWS ECR:

### 1. Build the Application

```bash
mvn clean package
```

### 2. Build Docker Image

```bash
docker build -t beginner349-app:latest .
```

### 3. Tag for ECR

```bash
docker tag beginner349-app:latest <AWS_ACCOUNT_ID>.dkr.ecr.<AWS_REGION>.amazonaws.com/beginner349-app:latest
```

### 4. Push to ECR

```bash
# Login to ECR
aws ecr get-login-password --region <AWS_REGION> | docker login --username AWS --password-stdin <AWS_ACCOUNT_ID>.dkr.ecr.<AWS_REGION>.amazonaws.com

# Push the image
docker push <AWS_ACCOUNT_ID>.dkr.ecr.<AWS_REGION>.amazonaws.com/beginner349-app:latest
```

## Testing

Run tests with:

```bash
mvn test
```

## Key Dependencies

| Dependency | Version | Purpose |
|-----------|---------|---------|
| Spring Boot | 4.0.1 | Framework foundation |
| Spring Security | Latest | Authentication & Authorization |
| Spring Data JPA | Latest | ORM & database access |
| AWS SDK DynamoDB | 2.40.16 | AWS DynamoDB integration |
| H2 Database | Latest | In-memory database for testing |


## Contributing

1. Create a feature branch (`git checkout -b feature/amazing-feature`)
2. Commit your changes (`git commit -m 'Add amazing feature'`)
3. Push to the branch (`git push origin feature/amazing-feature`)
4. Open a Pull Request

## Roadmap

- [ ] Kafka integration for event streaming
- [ ] Kubernetes deployment manifests
- [ ] CI/CD pipeline configuration
- [ ] Comprehensive API documentation (Swagger/OpenAPI)
- [ ] Performance benchmarking suite

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For issues, questions, or suggestions, please open an issue in the repository.

---

**Last Updated**: May 2026
**Java Version**: 25
**Spring Boot Version**: 4.0.1
