## Company Manager Application
### Running application
Requirements:
- installed docker

To run the application, execute the following command:
```shell
docker compose up --build
```

### Swagger
The application exposes endpoints for managing companies. 
These endpoints are documented using Swagger and are available at: http://localhost:8080/swagger-ui/index.html

### Running tests
Requirements:
- installed docker
- JDK 21

Integration tests verify basic functionality. To run the tests, execute the following command from the project's main directory:
```shell
./mvnw test
```