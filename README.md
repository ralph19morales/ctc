# Transaction Service

---

## Service Overview

The Transaction Service is responsible for handling the complete lifecycle of transactions and their associated domain entities:

- **Transactions**: Creation, management, and tracking of transaction records.
- **Fees**: Calculation and association of fees with transactions.
- **Fee Types**: Definition and management of various fee categories.

## Getting Started

### Running the Application Locally

1. **Clone the Repository**  
   Download the project from GitHub and extract the ZIP file.

2. **Navigate to the Project Directory**  
   Change to the `ctc` directory:

   ```sh
   cd ctc
   ```

3. **Start the Service**  
   Use `make up` to launch the application:

   ```sh
   make up
   ```

   ![Docker running](screenshots/docker.png)

4. **Access the API Documentation**  
   Open [http://localhost:8080/openapi](http://localhost:8080/openapi) in your browser to view and test the APIs using Swagger.
   ![Swagger UI](screenshots/swagger.png)

5. Use `./gradlew test` to run test
   ```sh
   ./gradlew test
   ```
   ![Example result](screenshots/kotest.png)

## Specifications

### Technology Stack

- **Language**: Kotlin
- **Framework**: Ktor
- **Dependency Injection**: Koin
- **Persistence**: JPA (Hibernate)
- **Database**: PostgreSQL
- **API Documentation**: OpenAPI (Swagger UI)
- **Build Tool**: Gradle
- **Containerization**: Docker, Docker Compose
- **Testing**: JUnit, Kotest
- **Logging**: Logback
- **Configuration**: YAML

## Architecture

The project follows hexagonal architecture. The domain contains the business logic which is completely isolated from the infrastructure. The controller accesses the domain through the application programming interfaces (APIs) while the resources needed by the domain is being injected by the providers by implementing the service provider interfaces (SPIs).

![](screenshots/hexagonal-screenshot.png)

## Moving forward

- More unit and integration tests
- Exception and error handling
- Identity management (authentication and authorization)
- Its good practice to have metrics, tracing and logging for observability
- Instead of docker, databases are advised to be hosted in a fully managed database-as-a-service solutions like AWS RDS
- Error monitoring like sentry would help in issue discovery and resolution
