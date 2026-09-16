Sure. I can give you a **single consolidated documentation file** that you can save as `BACKEND_DOCUMENTATION.md` and keep inside your project.

# STUDENT MANAGEMENT SYSTEM

## Backend Documentation

---

## 1\. Project Overview

The Student Management System is a RESTful backend application developed using Spring Boot.

The application provides APIs for user authentication, authorization, and student management. JWT (JSON Web Token) is used for authentication, while role-based authorization is implemented using Spring Security.

The main purpose of the project is to provide a secure backend API that can later be connected to a frontend application such as React or Angular.

### Main Features

- User registration
- User login
- Password encryption using BCrypt
- JWT authentication
- Role-based authorization
- Student CRUD operations
- Input validation
- Global exception handling
- Swagger/OpenAPI documentation
- CORS configuration
- MySQL database integration

---

# 2\. Technology Stack

| Technology | Purpose |
| --- | --- |
| Java | Programming language |
| Spring Boot | Backend framework |
| Spring Web | REST API development |
| Spring Data JPA | Database access |
| Hibernate | ORM |
| Spring Security | Authentication and authorization |
| JWT | Token-based authentication |
| BCrypt | Password encryption |
| MySQL | Database |
| Maven | Dependency management |
| Swagger/OpenAPI | API documentation and testing |
| Jakarta Validation | Request validation |

---

# 3\. Project Architecture

The project follows a layered architecture.

```
Client
   |
   v
Controller
   |
   v
Service
   |
   v
Repository
   |
   v
Database
```

Security is applied before protected requests reach the controller.

```
Client
   |
   | JWT Token
   v
JWT Authentication Filter
   |
   v
Spring Security
   |
   v
Controller
   |
   v
Service
   |
   v
Repository
   |
   v
MySQL
```

---

# 4\. Project Structure

```
src/main/java/com/student
│
├── config
│   ├── SecurityConfig.java
│   ├── OpenApiConfig.java
│   └── DataInitializer.java
│
├── controller
│   ├── AuthController.java
│   └── StudentController.java
│
├── dto
│   ├── Login_DTO.java
│   ├── User_DTO.java
│   └── ErrorResponse.java
│
├── entity
│   ├── User.java
│   ├── Student.java
│   └── Role.java
│
├── exception
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   ├── StudentNotFoundException.java
│   └── ResourceAlreadyExistsException.java
│
├── repository
│   ├── UserRepository.java
│   └── StudentRepository.java
│
├── security
│   ├── JwtService.java
│   ├── JwtAuthenticationFilter.java
│   ├── CustomUserDetailsService.java
│   ├── CustomAuthenticationEntryPoint.java
│   └── CustomAccessDeniedHandler.java
│
├── service
│   ├── UserService.java
│   ├── AuthService.java
│   └── StudentService.java
│
└── StudentManagementApplication.java
```

---

# 5\. Database

The application uses MySQL.

## User Table

The User table stores authentication and authorization information.

```
users
--------------------------------
id
username
email
password
role
```

Example:

```
1 | admin | admin@gmail.com | encrypted-password | ADMIN
2 | arun  | arun@gmail.com  | encrypted-password | USER
```

Passwords are stored using BCrypt encryption.

---

## Student Table

The Student table stores student information.

```
students
--------------------------------
id
first_name
last_name
email
course
age
```

The exact fields depend on the `Student` entity implemented in the project.

Student email is configured as unique to prevent duplicate records.

---

# 6\. User Roles

The application contains two roles.

```
ADMIN
USER
```

## USER

A USER can:

- Login
- View all students
- View individual student details

## ADMIN

An ADMIN can:

- Login
- View all students
- View individual student details
- Create students
- Update students
- Delete students

### Permission Table

| Operation | USER | ADMIN |
| --- | --- | --- |
| Login | Yes | Yes |
| View students | Yes | Yes |
| View student by ID | Yes | Yes |
| Create student | No | Yes |
| Update student | No | Yes |
| Delete student | No | Yes |

---

# 7\. Authentication

The application uses JWT-based authentication.

The login process works as follows:

```
Username + Password
        |
        v
AuthenticationManager
        |
        v
UserDetailsService
        |
        v
UserRepository
        |
        v
Database
        |
        v
BCrypt Password Verification
        |
        v
JWT Generation
        |
        v
JWT Token
```

After successful login, the client receives a JWT token.

Example:

```
{
    "username": "admin",
    "role": "ADMIN",
    "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

The client must send this token when accessing protected endpoints.

```
Authorization: Bearer <JWT_TOKEN>
```

---

# 8\. Password Security

Passwords are encrypted using BCrypt before being stored in the database.

Example:

```
passwordEncoder.encode(password);
```

The original password is never stored directly.

Example:

```
User password:
admin123

Stored password:
$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

This protects user passwords from being stored as plain text.

---

# 9\. JWT Authentication Filter

The `JwtAuthenticationFilter` is responsible for processing JWT tokens.

The filter:

1. Reads the Authorization header.
2. Checks for the `Bearer` prefix.
3. Extracts the JWT.
4. Extracts the username from the token.
5. Loads the user.
6. Validates the JWT.
7. Creates the Spring Security authentication object.
8. Stores authentication in the `SecurityContext`.

Flow:

```
HTTP Request
     |
     v
Authorization Header
     |
     v
Bearer JWT
     |
     v
JwtAuthenticationFilter
     |
     v
Validate JWT
     |
     v
Load UserDetails
     |
     v
Get Authorities
     |
     v
SecurityContext
     |
     v
Controller
```

---

# 10\. Role-Based Authorization

Spring Security is used to control access to APIs.

For ADMIN-only operations:

```
@PreAuthorize("hasRole('ADMIN')")
```

For operations available to both roles:

```
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
```

The application stores roles such as:

```
ADMIN
USER
```

Spring Security uses authorities:

```
ROLE_ADMIN
ROLE_USER
```

Therefore, an ADMIN user receives:

```
ROLE_ADMIN
```

and a USER receives:

```
ROLE_USER
```

---

# 11\. Authentication Endpoints

## Register

```
POST /api/auth/register
```

### Request

```
{
    "username": "arun",
    "email": "arun@gmail.com",
    "password": "password123"
}
```

### Successful Response

```
201 Created
```

Example:

```
{
    "id": 1,
    "username": "arun",
    "email": "arun@gmail.com",
    "role": "USER"
}
```

New users are assigned the `USER` role by default.

---

## Login

```
POST /api/auth/login
```

### Request

```
{
    "username": "arun",
    "password": "password123"
}
```

### Successful Response

```
200 OK
```

Example:

```
{
    "username": "arun",
    "role": "USER",
    "token": "eyJ..."
}
```

### Invalid Credentials

```
401 Unauthorized
```

Example:

```
{
    "status": 401,
    "message": "Invalid username or password"
}
```

---

# 12\. Student Endpoints

## Get All Students

```
GET /api/students
```

Authentication:

```
Required
```

Roles:

```
USER
ADMIN
```

### Response

```
200 OK
```

Example:

```
[
    {
        "id": 1,
        "firstName": "Arun",
        "lastName": "Kumar",
        "email": "arun@gmail.com",
        "course": "Java",
        "age": 22
    }
]
```

---

## Get Student by ID

```
GET /api/students/{id}
```

Example:

```
GET /api/students/1
```

Authentication:

```
Required
```

Roles:

```
USER
ADMIN
```

If the student does not exist:

```
404 Not Found
```

---

## Create Student

```
POST /api/students
```

Authentication:

```
Required
```

Role:

```
ADMIN
```

### Request

```
{
    "firstName": "Arun",
    "lastName": "Kumar",
    "email": "arun@gmail.com",
    "course": "Java",
    "age": 22
}
```

### Response

```
201 Created
```

A USER attempting this operation receives:

```
403 Forbidden
```

---

## Update Student

```
PUT /api/students/{id}
```

Authentication:

```
Required
```

Role:

```
ADMIN
```

### Request

```
{
    "firstName": "Arun",
    "lastName": "Kumar",
    "email": "arun@gmail.com",
    "course": "Spring Boot",
    "age": 23
}
```

### Response

```
200 OK
```

---

## Delete Student

```
DELETE /api/students/{id}
```

Authentication:

```
Required
```

Role:

```
ADMIN
```

### Response

```
200 OK
```

If the student does not exist:

```
404 Not Found
```

---

# 13\. Student Validation

Jakarta Bean Validation is used to validate incoming requests.

Examples:

```
@NotBlank
```

Used for required text fields.

```
@Email
```

Used to validate email addresses.

```
@NotNull
```

Used for required values.

```
@Min(5)
```

Used to ensure a minimum numeric value.

The controller uses:

```
@Valid
```

Example:

```
@PostMapping
public ResponseEntity<Student> createStudent(
        @Valid @RequestBody Student student) {

    return ResponseEntity.status(HttpStatus.CREATED)
            .body(studentService.createStudent(student));
}
```

Invalid input results in:

```
400 Bad Request
```

---

# 14\. Exception Handling

The application uses a centralized exception handler.

Main class:

```
GlobalExceptionHandler
```

It uses:

```
@RestControllerAdvice
```

This allows exceptions from different controllers to be handled consistently.

The application handles:

```
400 Bad Request
401 Unauthorized
403 Forbidden
404 Not Found
409 Conflict
```

---

# 15\. Student Not Found

A custom exception is used:

```
StudentNotFoundException
```

Example:

```
GET /api/students/999
```

If student `999` does not exist:

```
404 Not Found
```

Response:

```
{
    "status": 404,
    "message": "Student not found with id: 999",
    "timestamp": "2026-09-11T19:30:00"
}
```

---

# 16\. Duplicate Student

Student email must be unique.

Before creating a student, the service checks:

```
studentRepository.existsByEmail(email);
```

If the email already exists:

```
409 Conflict
```

Example:

```
{
    "status": 409,
    "message": "Student with email arun@gmail.com already exists",
    "timestamp": "2026-09-11T19:30:00"
}
```

---

# 17\. HTTP Status Codes

| Status Code | Meaning |
| --- | --- |
| 200 | Request successful |
| 201 | Resource created |
| 400 | Invalid request |
| 401 | Authentication required/failed |
| 403 | Access denied |
| 404 | Resource not found |
| 409 | Resource already exists |

---

# 18\. 401 Authentication Handling

When a user accesses a protected API without valid authentication, the custom authentication entry point returns:

```
401 Unauthorized
```

Example:

```
{
    "status": 401,
    "message": "Authentication required"
}
```

---

# 19\. 403 Authorization Handling

When a logged-in USER tries to access an ADMIN-only API, the custom access denied handler returns:

```
403 Forbidden
```

Example:

```
{
    "status": 403,
    "message": "You do not have permission to perform this operation"
}
```

---

# 20\. Security Configuration

The application uses stateless JWT authentication.

Important configuration:

```
SessionCreationPolicy.STATELESS
```

This means the server does not maintain a traditional login session.

Every protected request must contain the JWT.

Security flow:

```
Request
   |
   v
JWT Filter
   |
   v
JWT Validation
   |
   v
Authentication
   |
   v
Authorization
   |
   +---- Allowed ----> Controller
   |
   +---- Not allowed -> 403
   |
   +---- Not authenticated -> 401
```

---

# 21\. CORS Configuration

CORS is configured so that a frontend application can communicate with the backend.

Development origins may include:

```
http://localhost:3000
http://localhost:5173
```

Allowed methods:

```
GET
POST
PUT
DELETE
OPTIONS
```

The Authorization header is allowed so the frontend can send JWT tokens.

---

# 22\. Swagger / OpenAPI

Swagger is used to document and test the REST APIs.

Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

JWT authorization is configured in Swagger.

### Swagger Testing Process

```
1. Open Swagger UI
       |
2. Execute Login API
       |
3. Copy JWT token
       |
4. Click Authorize
       |
5. Enter JWT token
       |
6. Execute protected APIs
```

Swagger automatically sends:

```
Authorization: Bearer <JWT>
```

with protected requests.

---

# 23\. Application Configuration

Typical `application.properties` configuration:

```
spring.application.name=student-management-system

spring.datasource.url=jdbc:mysql://localhost:3306/student_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=your-secret-key
jwt.expiration=3600000
```

Sensitive information such as database passwords and JWT secrets should not be committed to source control in a production application.

---

# 24\. Development Admin Account

For development and testing, an initial ADMIN account may be created using a data initializer.

Example:

```
Username: admin
Password: admin123
Role: ADMIN
```

This account is intended only for development/testing.

Production applications should use secure credential management.

---

# 25\. Complete API Summary

| Method | Endpoint | Authentication | Role |
| --- | --- | --- | --- |
| POST | `/api/auth/register` | No | Public |
| POST | `/api/auth/login` | No | Public |
| GET | `/api/students` | Yes | USER / ADMIN |
| GET | `/api/students/{id}` | Yes | USER / ADMIN |
| POST | `/api/students` | Yes | ADMIN |
| PUT | `/api/students/{id}` | Yes | ADMIN |
| DELETE | `/api/students/{id}` | Yes | ADMIN |

---

# 26\. Complete Login Flow

```
                    CLIENT
                      |
                      |
              username/password
                      |
                      v
              POST /api/auth/login
                      |
                      v
                AuthController
                      |
                      v
                  AuthService
                      |
                      v
            AuthenticationManager
                      |
                      v
             UserDetailsService
                      |
                      v
                UserRepository
                      |
                      v
                  MySQL
                      |
                      v
             BCrypt Verification
                 /          \
              FAIL          SUCCESS
               |               |
               v               v
              401          JWT Service
                               |
                               v
                           JWT Token
                               |
                               v
                             Client
```

---

# 27\. Complete Protected Request Flow

```
CLIENT
  |
  | Authorization: Bearer JWT
  |
  v
JwtAuthenticationFilter
  |
  v
Extract JWT
  |
  v
Validate JWT
  |
  v
Load UserDetails
  |
  v
Set SecurityContext
  |
  v
Check User Role
  |
  +---------------------+
  |                     |
  v                     v
USER                  ADMIN
  |                     |
  v                     v
Read APIs          CRUD APIs
  |                     |
  +----------+----------+
             |
             v
         Controller
             |
             v
          Service
             |
             v
         Repository
             |
             v
           MySQL
```

---

# 28\. Error Handling Flow

```
                    Request
                       |
                       v
                 Spring Security
                       |
          +------------+------------+
          |                         |
       Invalid                   Valid
          |                         |
         401                  Authorization
                                    |
                           +--------+--------+
                           |                 |
                        Allowed           Denied
                           |                 |
                           v                403
                      Controller
                           |
                           v
                        Service
                           |
                  +--------+--------+
                  |                 |
                Success           Error
                  |                 |
                  v                 v
                200/201          Exception
                                    |
                                    v
                           GlobalExceptionHandler
                                    |
                                    v
                              Error Response
```

---

# 29\. Testing Checklist

## Authentication

- [ ] Register with valid data
- [ ] Register with invalid data
- [ ] Register duplicate username
- [ ] Register duplicate email
- [ ] Login with valid credentials
- [ ] Login with invalid password
- [ ] Login with invalid username

## JWT

- [ ] Access protected API without token
- [ ] Access protected API with valid token
- [ ] Access protected API with invalid token
- [ ] Test expired token

## Authorization

- [ ] USER can view students
- [ ] USER cannot create student
- [ ] USER cannot update student
- [ ] USER cannot delete student
- [ ] ADMIN can view students
- [ ] ADMIN can create student
- [ ] ADMIN can update student
- [ ] ADMIN can delete student

## Student Management

- [ ] Create valid student
- [ ] Reject invalid student
- [ ] Reject duplicate email
- [ ] Get all students
- [ ] Get student by ID
- [ ] Handle non-existing student
- [ ] Update student
- [ ] Delete student

## Swagger

- [ ] Swagger UI opens
- [ ] Login API works
- [ ] JWT can be entered using Authorize
- [ ] Protected APIs work with JWT
- [ ] USER receives 403 for ADMIN operations
- [ ] ADMIN can perform CRUD operations

---

# 30\. Backend Completion Status

```
Project Setup                         ✅
Spring Boot                           ✅
Maven                                 ✅
MySQL                                 ✅
JPA / Hibernate                       ✅

Entity Layer                          ✅
Repository Layer                      ✅
Service Layer                         ✅
Controller Layer                      ✅

User Registration                     ✅
User Login                            ✅
BCrypt Password Encryption            ✅

JWT Generation                        ✅
JWT Validation                        ✅
JWT Authentication Filter             ✅

USER Role                             ✅
ADMIN Role                            ✅
Role-Based Authorization              ✅

Request Validation                    ✅
Global Exception Handling             ✅
401 Handling                          ✅
403 Handling                          ✅
404 Handling                          ✅
409 Handling                          ✅

Swagger / OpenAPI                     ✅
Swagger JWT Authorization             ✅
CORS                                  ✅
```

---

# 31\. Future Improvements

The following features can be added later:

- Student DTOs for request/response separation
- Pagination
- Sorting
- Searching students
- Filtering by course
- Student profile management
- Attendance management
- Marks/grades management
- Course management
- Admin dashboard
- Refresh tokens
- Email verification
- Password reset
- Audit logging
- Unit tests
- Integration tests
- Docker deployment
- Production environment configuration

---

# 32\. Conclusion

The Student Management System backend is a secure RESTful Spring Boot application that provides authentication, authorization, and student management functionality.

The application follows a layered architecture consisting of:

```
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

Security is implemented using:

```
Spring Security
       +
JWT
       +
BCrypt
       +
Role-Based Authorization
```

The backend also includes:

```
Validation
Exception Handling
Swagger
CORS
MySQL
JPA/Hibernate
```

The backend is ready to be integrated with a frontend application.

The next stage of the project is to build the frontend and connect it to the REST APIs using JWT authentication.

Save that as **`BACKEND_DOCUMENTATION.md`** in the root of your Spring Boot project.