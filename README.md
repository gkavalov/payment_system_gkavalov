# Payment System 
## Practical Task by Georgi Kavalov

### Local development
#### Prerequisites:
 - JDK 21
 - Maven 3.9.6 (maven wrapper is provided for your convenience)
 - PostgreSQL 16.2
 - Docker 26.0.0

Project is buildable and runnable:
```bash
mvnw clean package -Pdev
mvnw spring-boot:run
```
Note that the dev profile is used for local development. If you want to 
start the application in a docker environment build it using the default provide (omitting the -Pdev option)

### Docker
The solution is dockerised and can be run (along with a database):
```bash
docker-compose up
```

### Tests
Test coverage is at 65% of code lines.

### UI
Unfortunately a web portal UI is not implemented.
However, I have provided simple Swagger documentation accessible at: <a href="http://localhost:8080/v1/swagger-ui/index.html">SwaggerUI</a>

### Future maintenance
There are various places which I have marked with `// TODO` comments
identifying what is missing from the functionality.

