# Project Architecture & Code Analysis (PROJECT_EXPLANATION_ENG.md)

This document is designed to explain the logic running in the background, security flow, exception handling, and inter-module communication in detail.

## 1. Overview and Layered Architecture

The project follows the **Layered Architecture** pattern, which is standard in enterprise applications. A request passes through the following layers respectively:

```mermaid
graph LR
    User[Client/User] --> Controller
    Controller --> Service
    Service --> Repository
    Repository --> Database[(Database)]
```

1.  **Controller (Presentation Layer):** Handles incoming HTTP requests (GET, POST, etc.) from the outside world. Receives parameters and forwards them to the Service layer.
2.  **Service (Business Layer):** The brain of the project. Business rules run here (e.g., "Is the user already registered?", "Is there stock?").
3.  **Repository (Data Access Layer):** The only layer that talks to the database. It retrieves or saves data using JPA methods instead of raw SQL queries.
4.  **Database (Data Layer):** Our PostgreSQL database.

---

## 2. Security Architecture (Security & JWT)

The security layer is a **filter** mechanism that protects our application. Before a request reaches the Controller, it passes through a control point called the _Security Filter Chain_.

### Request Control Flow (Security Flow)

The diagram below shows what happens when a user wants to access a protected page:

```mermaid
sequenceDiagram
    participant User as User
    participant Filter as JwtAuthFilter (Gatekeeper)
    participant SecCtx as SecurityContext (System)
    participant Controller as API (Controller)

    User->>Filter: Send Request (Header: Bearer <token>)
    Filter->>Filter: Is Token present and valid?

    alt Token Invalid or Missing
        Filter-->>User: 403 Forbidden (Access Denied)
    else Token Valid
        Filter->>SecCtx: Save Identity Info (Authentication)
        Filter->>Controller: Pass Request
        Controller-->>User: Success Response (200 OK)
    end
```

### Key Components

- **`SecurityConfig`**: The constitution determining who can enter where.
  - `/auth/**` (Login/Register) -> Open to everyone (PermitAll).
  - `/actuator/**` and `/swagger-ui/**` -> Open for documentation and monitoring.
  - **Everything else** -> Open only to authenticated users.
- **`JwtAuthenticationFilter`**: The police officer stopping every incoming request. It asks, "Do you have an ID (Token)?". If yes, it verifies and lets you in.

---

## 3. Dynamic Exception Handling

When a problem occurs in the application (e.g., "User not found"), instead of showing complex Java error messages (Stack Trace) to the user, we return **understandable and structured** JSON responses.

The class providing this is: **`GlobalExceptionHandler`**

This class listens to the entire application and intervenes when an exception is thrown.

**Example Scenario:** User tries to register with an invalid email.

**Standard Java Error (Bad):**
`ConstraintViolationException: email invalid format...`

**Our Response (Good):**

```json
{
  "status": "ERROR",
  "message": "Validation error",
  "errorDetails": {
    "code": "VALIDATION_ERROR",
    "details": "Invalid input data",
    "fieldErrors": {
      "email": ["Please enter a valid email address"],
      "password": ["Password must be at least 6 characters"]
    }
  }
}
```

Thanks to this structure, the Frontend developer easily understands in which field the error occurred and what to tell the user.

---

## 4. Entities and Relationships (Data Model)

These are the classes corresponding to tables in the database (Entity).

- **User:** People logging into the system. (Name, email, password, role).
- **Order:** (To be developed) Orders placed by the user.

Relationship: **One User can have MANY orders (One-to-Many).**

---

## 5. Summary

This project is designed not just to write code, but to establish a **maintainable, secure, and extensible** infrastructure.

1.  **Modularity:** Each module (Auth, User) is independent of each other.
2.  **Security:** Stateless security is provided with JWT.
3.  **Quality:** Data integrity is protected with Exception Handling and Validations.
4.  **Observability:** The system can be X-rayed with Swagger and Actuator.
