## Library Management System   
---
---   
A mini library management system built with **Java 21 & Spring Boot**, designed for learning real-world backend architecture and showchasing **professional backend** skills.    
### CONTENTS
---
#### Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security (Basic Auth)
- RabbitMQ (Event-driven)
- H2 Database
- ModelMapper
- SLF4J / Logback
- RESTful API
- Maven
- Lombok
---
#### Architecture
This project follows a Layered Architecture:
```nginx
Controller → Service → Repository → Database
```
Additionally, it uses:
- DTO Pattern for API Security
- Event-driven architecture with RabbitMQ
- Global Exception Handling
- Transactional business logic
---
#### 🐰 Event-Driven Flow (RabbitMQ)
```pgsql
User Action
   ↓
Service Layer
   ↓
Domain Event Published
   ↓
RabbitMQ
   ↓
Async Consumer
   ↓
Audit Log
```
All critical actions (register, login, book operations, rental, return) are logged asynchronously.
#### Security:
- /auth/** endpoints are public
- All other endpoints require authentication
- HTTP Basic Authentication
- Stateless REST API
>[!NOTE]
Security is intentionally kept simple for learning purpose. The architecture is ready for JWT integration.
---
#### Core Features    
- User
  - Register
  - Logging (password encrypted with BCrypt)
- Book
  - Create book
  - List all books
  - Stock Management
- Rental
  - Rent a book
  - Return a book
  - Stock auto update
  - Transactional safety
- Audit Logging
  - All important actions are logged aysnchronously via RabbitMQ
---
#### API Endpoints    
Auth:
```bash
POST   /auth/register
POST   /auth/login
```
Books:
```bash
POST   /books
GET    /books
```
Rentals:
```bash
POST   /rentals/rent
POST   /rentals/return/{rentalId}
```
---
#### What I Learned?
- Layered backend architecture
- DTO & ModelMapper usage
- Spring Security fundamentals
- Transaction management
- RabbitMQ with async consumers
- Global Exception Handling
- Clean code & separaration of concerns
---
#### How to Run?
```bash
mvn spring-boot:run
```
RabbitMQ(Docker)
```bash
docker run -it --rm \
  -p 5672:5672 -p 15672:15672 \
  rabbitmq:3-management
```
---
#### Future Improvements 📌
- JWT Authentication
- Role-based aurhorization
- PostgreSQL
- Pagination & filtering
- OpenAPI (Swagger)
- Integration Tests
---
#### Author    
Omur - Junior Backend Developer (Java & Spring Boot)
