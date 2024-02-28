## clients-database

This project is designed to implement a simple database that stores information about clients and their contact details.

### Database Design:
- Each client is characterized by a name.
- Each client can have multiple contact details: zero or more phone numbers, zero or more email addresses.

### API Features:
1. **Add a new client**
2. **Add a new contact for a client (phone number or email)**
3. **Get a list of clients**
4. **Get information about a specific client (by id)**
5. **Get a list of contacts for a specific client**
6. **Get a list of contacts of a specific type for a specific client**

### Technologies:
- Spring Framework
- Spring Boot
- Spring Data JPA
- Hibernate Validator
- Liquibase
- PostgreSQL
- Lombok
- MapStruct
- Swagger

### Dependencies:
Please refer to the `pom.xml` file in the project for a detailed list of dependencies.

### Build Instructions:
This project is built using Maven. You can run the project using the `spring-boot:run` Maven goal.

Feel free to reach out if you have any questions or need further information! 🌟
