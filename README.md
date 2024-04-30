# RESTful API for User Management

This project represents an implementation of a RESTful API for user management using Spring Boot.

## Task Description
The task consists of two parts:
1. Learning about RESTful API design principles and best practices.
2. Implementing the RESTful API for user management based on a Spring Boot web application.

### Resources
- [RESTful API Design: Best Practices in a Nutshell](https://restfulapi.net/)
- [Error Handling for REST with Spring | Baeldung](https://www.baeldung.com/rest-api-error-handling-best-practices)
- [Testing in Spring Boot | Baeldung](https://www.baeldung.com/spring-boot-testing)
- [Testing | Spring](https://spring.io/guides/gs/testing-web/)

### Requirements
1. **User Fields:**
    - Email (required): Validation against email pattern.
    - First Name (required)
    - Last Name (required)
    - Birth Date (required): Value must be earlier than the current date.
    - Address (optional)
    - Phone Number (optional)

2. **Functionality:**
    - Create User: Registration of users who are more than 18 years old (age value from properties file).
    - Update User Fields: Partial or complete update of user information.
    - Delete User
    - Search Users by Birth Date Range: Validation ensuring "From" date is less than "To" date. Returns a list of users.

3. **Testing:**
    - Unit tests using Spring framework.

4. **Error Handling:**
    - Proper error handling for RESTful responses.

5. **Response Format:**
    - API responses should be in JSON format.

6. **Database Usage:**
    - Data persistence layer is not required.

# Implementation Details

## In-Memory User Repository Implementation

For the purpose of this assignment, a simple in-memory repository was implemented to manage user data. Since database connectivity was not required, users are stored in a map using a separate implementation. Here are the key features of this implementation:

- **Data Storage**: User data is stored in an in-memory map, providing a lightweight and efficient way to manage user records.

- **Unique Email Validation**: When creating or updating a user, the repository ensures that each user's email is unique. This is achieved by performing a check against existing user emails before adding or updating a user.

- **Auto-Generated IDs**: Each user is assigned a unique ID automatically when they are created. This ID is incremented atomically to ensure uniqueness across users.


- Implemented CRUD operations for user management.
- Added validation for user fields such as email pattern and phones.
- Developed error handling for RESTful responses.
- Ensured API responses are in JSON format.
- Covered the code with unit tests using Spring framework.

- **Swagger** was used to document the RESTful API endpoints implemented in the Spring Boot application. Here are the key points regarding the Swagger documentation:

## Docker Setup for Project

A Dockerfile and docker-compose.yaml were created to facilitate the deployment and running of the project in a Docker environment.

## CORS Configuration

CORS (Cross-Origin Resource Sharing) configuration was implemented to allow requests from different origins. This ensures that the API can be accessed by clients running on different domains. The CORS configuration includes allowing specific HTTP methods, headers, and origins to access the API endpoints securely.


# Contact Information

Feel free to reach out to me for any inquiries, feedback, or support:

- **Email:** [4pnav21@gmail.com](mailto:4pnav21@gmail.com)
- **LinkedIn:** [Aleksey Nikitin](https://www.linkedin.com/in/aleksey-nikitin-62728414b/)
- **GitHub:** [AlexNik13](https://github.com/AlexNik13)
- **Telegram:** [@Alexey_Nikitin1313](https://t.me/Alexey_Nikitin1313)

Don't hesitate to contact me through any of the above channels. I'm here to help!
