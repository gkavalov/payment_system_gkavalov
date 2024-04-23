# Payment System 
## Practical Task by Georgi Kavalov

### Project state
Project is buildable and runnable.
Prerequisites for local development:
 - JDK 21
 - maven wrapper is provided for your
convenience.
```
mvnw clean package
mvnw spring-boot:run
```
### Tests
Test coverage is at 65% of code lines.

### Docker
The solution is dockerised and can be run (along with a database):
```
docker-compose up
```

### UI
Unfortunately a web portal UI is not implemented.
However, I have provided simple Swagger documentatino accessible at:
```
http://localhost:8080/v1/swagger-ui/index.html
```

### Future maintenance
There are various places which I have marked with `// TODO` comments
identifying what is missing from the functionality.

