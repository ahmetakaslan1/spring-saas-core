# Order Management System

This project is an Order Management System developed using Spring Boot and modern Java technologies, designed with a modular architecture. It includes production-ready features such as advanced logging, validation, and API documentation.

## 🚀 Project Structure & Modular Approach

The project is designed with an "Independent Modules" architecture. This ensures that each feature is isolated under its own package.

<details>
<summary>📂 <strong>View Project Folder Structure (Click to Expand)</strong></summary>

```
order-management
├── src/main/java/com/ahmet/order_management
│   ├── auth             # Authentication operations (Login, Register, Token)
│   │   ├── controller   # AuthController (API Endpoints)
│   │   ├── dto          # RegisterRequest, LoginResponse etc.
│   │   └── service      # AuthService (Business Logic)
│   ├── common           # Shared components
│   │   ├── dto          # ApiResponse, ErrorDetails
│   │   ├── exception    # GlobalExceptionHandler (Error Handling)
│   │   └── util         # JwtUtil etc.
│   ├── config           # System configurations
│   │   ├── SecurityConfig.java # JWT and Security rules
│   │   └── OpenApiConfig.java  # Swagger settings
│   ├── user             # User module
│   │   ├── controller   # UserController
│   │   ├── entity       # User Entity (Database mapping)
│   │   └── service      # UserService
│   └── OrderManagementApplication.java # Launcher class
├── src/main/resources
│   └── application.yaml # Application settings
├── docker-compose.yml   # Docker setup file
├── Dockerfile           # Container image file
└── pom.xml              # Dependencies (Maven)
```

</details>

When exploring the codebase, you will see the following structure:

- **`auth`**: Authentication, Login/Register operations, and Token management.
- **`user`**: User profile management and user-specific operations.
- **`order`**: (Under development) Order management module.
- **`config`**: Security configurations and application settings affecting the whole system.
- **`common`**: Exception handling and utility classes used by all modules.

Thanks to this structure, any module of the project can be easily ported to another project or developed independently.

## 🛠 Technologies & Libraries

The project features a comprehensive stack of modern technologies:

### Core Infrastructure

- **Java 17**: The comprehensive LTS Java version.
- **Spring Boot 3.2**: Framework for fast and secure web application development.
- **PostgreSQL**: Powerful and open-source relational database.
- **Maven**: Project dependency and build management.

### Security

- **Spring Security**: Industry-standard security framework.
- **JWT (JSON Web Token)**: Stateless authentication mechanism.
- **BCrypt**: Secure password hashing algorithm.

### Data Management & Validation

- **Spring Data JPA (Hibernate)**: ORM layer for database operations.
- **Spring Validation**: Ensures data integrity by validating API request inputs (Pattern, NotBlank, Size, etc.).

### Monitoring & Documentation

- **Spring Boot Actuator**: Used for monitoring application health (Health Check) and metrics.
- **OpenAPI / Swagger (SpringDoc)**: Automatically generates API documentation and provides a test interface.
- **SLF4J & Logback**: Integrated advanced logging infrastructure. All critical system events and errors are logged in detail.

### Productivity

- **Lombok**: Reduces boilerplate code by automatically generating Getters, Setters, Constructors, etc.

## ⚙️ Installation & Setup

We recommend using **Docker** to run the project in your local environment most easily.

### 1. Prerequisites

- Make sure [Docker Desktop](https://www.docker.com/products/docker-desktop/) is installed and running on your machine.
- Clone the project:
  ```bash
  git clone https://github.com/ahmetakaslan1/spring-saas-core.git
  cd spring-saas-core
  ```

### **Detailed Project & Code Analysis**

For **in-depth technical documentation** regarding the project architecture, security flow, and code structure, please refer to the [PROJECT_EXPLANATION_ENG.md](https://github.com/ahmetakaslan1/spring-saas-core/blob/master/PROJECT_EXPLANATION_ENG.md) file.

### 2. Environment Variables (.env)

The project reads settings from the `.env` file. Create your own `.env` file by copying the example:

```bash
# Windows (PowerShell)
copy .env.example .env

# Linux / Mac
cp .env.example .env
```

You can open the created `.env` file with a text editor and make necessary changes (default settings are suitable for Docker).

### 3. Start the Application (Docker)

Run the following command to start both the database and the application at once:

```bash
docker-compose up -d --build
```

This process might take some time on the first run as it downloads the database image and application dependencies.

- Application URL: `http://localhost:8080`
- Swagger UI (Documentation): `http://localhost:8080/swagger-ui/index.html` (Available when service is running)

### 4. Development Environment (Optional)

If you wish to run it via an IDE (IntelliJ, VS Code, etc.) without Docker:

1.  Create a PostgreSQL database locally.
2.  Update `DB_HOST` in the `.env` file or `application.yaml` to `localhost`.
3.  Start the project by running the `OrderManagementApplication.java` file via your IDE.

---

👨‍💻 Geliştirici: Ahmet Akaslan
