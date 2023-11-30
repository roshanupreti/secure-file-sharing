# Secure File Sharing Application

This repository contains the Secure File Sharing application, a multi-module Spring Boot project. It is designed to demonstrate a basic file-sharing platform with a focus on secure and efficient data handling.

## Modules

- **Application Module**: The main module containing the Spring Boot application.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java 17
- Maven
- Docker and Docker Compose (for containerization)

### Installing

A step-by-step series of examples that tell you how to get a development environment running:

1. **Clone the Repository**

    ```
    git clone https://github.com/roshanupreti/secure-file-sharing.git
    ```

2. **Build the Application**

   Use Maven to build the application:

    ```
    mvn clean install
    ```

   This command will compile the application and create an executable JAR file in the `target` directory, in application module.

### Running the Application

#### Running Locally Without Docker

To run the application directly without Docker:

```
    java -jar application/target/application-1.0-SNAPSHOT.jar
```


#### Running with Docker Compose

To run the application as a Docker container, use the following steps:

1. **Start Services with Docker Compose, from project root.**

    ```
    docker-compose up
    ```

   This command will start the application along with any other services defined in `docker-compose.yml`.


2. **Access the Application**

The application will be accessible at `http://localhost:8080`.

## Configuration

- The application's configuration is located in the `application.properties` file in the application module.
- Adjust database and other configurations as necessary.

## Built With

- [Spring Boot](https://spring.io/projects/spring-boot) - The web framework used
- [Maven](https://maven.apache.org/) - Dependency Management
- [Docker](https://www.docker.com/) - Containerization

## Authors

- **Roshan Upreti** - [roshanupreti](https://github.com/roshanupreti)


