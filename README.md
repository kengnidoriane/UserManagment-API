# User Management API

A RESTful API for user management and authentication built with Spring Boot, Spring Security, JWT, and PostgreSQL database.

## Features

- User registration and authentication
- JWT-based security
- CRUD operations for user management
- Password encryption with BCrypt
- Swagger API documentation
- PostgreSQL database

## Technologies

- **Spring Boot 3.5.7**
- **Spring Security** (JWT Authentication)
- **Spring Data JPA**
- **PostgreSQL Database**
- **Swagger/OpenAPI 3**
- **BCrypt** password encoding
- **Maven**

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6+
- PostgreSQL 12+ installed and running

### Installation & Running

1. **Clone the repository**
```bash
git clone 'https://github.com/kengnidoriane/UserManagment-API'
cd UserManagment-API
```

2. **Setup PostgreSQL database**
```sql
CREATE DATABASE user_management_db;
```

3. **Configure environment variables**
Create a `.env` file in the project root by copying `.env.example`:
```bash
cp .env.example .env
```

Then update the `.env` file with your configuration:
```bash
# Database Configuration
DB_URL=jdbc:postgresql://localhost:5432/user_management_db
DB_USERNAME=your_postgres_username
DB_PASSWORD=your_postgres_password

# JWT Configuration (generate a secure secret key)
JWT_SECRET=your_super_secret_jwt_key_here_at_least_32_characters
JWT_EXPIRATION=86400000
```

**Generate a secure JWT secret:**
```bash
# Linux/Mac
openssl rand -base64 64

# Windows PowerShell
[System.Web.Security.Membership]::GeneratePassword(64, 0)
```

4. **Build the project**
```bash
mvn clean install
```

5. **Run the application**
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

Access the Swagger UI at: **http://localhost:8080/swagger-ui.html**

## Testing the API

### 1. Register a New User

**Endpoint:** `POST /api/auth/register`

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com"
}
```

### 2. Login User

**Endpoint:** `POST /api/auth/login`

**Request Body:**
```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "user": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com"
  }
}
```

### 3. Access Protected Endpoints

For all `/api/users/*` endpoints, you need to include the JWT token in the Authorization header:

**Header:**
```
Authorization: Bearer <your-jwt-token>
```

#### Get All Users
**Endpoint:** `GET /api/users`

#### Get User by ID
**Endpoint:** `GET /api/users/{id}`

#### Update User
**Endpoint:** `PUT /api/users/{id}`

**Request Body:**
```json
{
  "name": "John Updated",
  "email": "john.updated@example.com",
  "password": "newpassword123"
}
```

#### Delete User
**Endpoint:** `DELETE /api/users/{id}`

## Testing with Swagger UI

1. **Open Swagger UI:** http://localhost:8080/swagger-ui.html

2. **Register a user:**
   - Expand `Authentication` section
   - Click on `POST /api/auth/register`
   - Click "Try it out"
   - Fill in the request body
   - Click "Execute"

3. **Login to get JWT token:**
   - Click on `POST /api/auth/login`
   - Use the same credentials from registration
   - Copy the `token` from the response

4. **Authorize for protected endpoints:**
   - Click the "Authorize" button (🔒) at the top
   - Enter: `Bearer <your-copied-token>`
   - Click "Authorize"

5. **Test protected endpoints:**
   - Now you can test all `/api/users/*` endpoints
   - The JWT token will be automatically included

## Database Access

**PostgreSQL Database:** `user_management_db`

**Connection Details:**
- **Host:** `localhost`
- **Port:** `5432`
- **Database:** `user_management_db`
- **Username:** `postgres`
- **Password:** `password`

## API Endpoints Summary

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/register` | Register new user | No |
| POST | `/api/auth/login` | Login user | No |
| GET | `/api/users` | Get all users | Yes |
| GET | `/api/users/{id}` | Get user by ID | Yes |
| PUT | `/api/users/{id}` | Update user | Yes |
| DELETE | `/api/users/{id}` | Delete user | Yes |

## Error Responses

- **400 Bad Request:** Invalid input data
- **401 Unauthorized:** Missing or invalid JWT token
- **404 Not Found:** User not found
- **409 Conflict:** Email already exists

## Configuration

Key configuration in `application.properties` (uses environment variables):

```properties
# Database
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/user_management_db}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:password}

# JWT
jwt.secret=${JWT_SECRET:mySecretKey123456789012345678901234567890}
jwt.expiration=${JWT_EXPIRATION:86400000}

# Swagger
springdoc.swagger-ui.path=/swagger-ui.html
```

**Environment Variables (.env file):**
```bash
DB_URL=jdbc:postgresql://localhost:5432/user_management_db
DB_USERNAME=postgres
DB_PASSWORD=your_secure_password
JWT_SECRET=your_generated_secure_jwt_secret
JWT_EXPIRATION=86400000
```

## Security Notes

- Passwords are encrypted using BCrypt
- JWT tokens expire after 24 hours
- Email addresses must be unique
- All user endpoints require valid JWT authentication