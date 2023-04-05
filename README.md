# Insurance-Management-System  

This is an Insurance Management System built using Spring Boot and Java. The system allows users to manage insurance policies, clients, and claims. Users can create, view, update, and delete policies, clients, and claims using RESTful APIs. The system also includes basic authentication to secure the APIs.

## Project Structure

The project follows a standard Spring Boot project structure:  

- **src/main/java/com.example.ims**: Contains the main application files and packages.    

  - **model**: Contains the domain model classes.    
  
  - **repository**: Contains Spring Data JPA repositories.
  
  - **controller**: Contains RESTful API controllers.
  
  - **security**: Contains security configuration.    

- **src/main/resources**: Contains resource files for the application.    

  - **application.properties**: Contains application configuration properties.
  
## Features

- Create, view, update, and delete clients
- Create, view, update, and delete insurance policies
- Create, view, update, and delete claims

## How to run the application locally

To run the application locally, follow these steps:

1. Clone the repository from Github.  

2. Navigate to the project directory.  

3. Build the application using Maven: `mvn clean`

4. Run the application. `mvn spring-boot:run`  

5. The application should be running on http://localhost:8080
