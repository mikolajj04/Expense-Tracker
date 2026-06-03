# Personal Expense Tracker API

## Project Overview
A robust RESTful API designed for comprehensive personal finance management. The system allows users to track daily expenses, manage custom categories, and set strict monthly budget limits.

The project was developed with a strong emphasis on **Clean Architecture**, **Design Patterns**, and transactional data integrity, serving as the capstone project for the **ING Corporate Readiness Certificate (CRC)** program.

## 🛠️ Tech Stack
- **Language:** Java 21 (LTS)
- **Framework:** Spring Boot 3
- **Database:** PostgreSQL 16
- **Persistence:** Spring Data JPA / Hibernate & pure JDBC (`JdbcTemplate`)
- **Containerization:** Docker & Docker Compose
- **Testing:** JUnit 5, Mockito
- **Tools:** Lombok, Maven

## 🏗️ Architectural Highlights
* **Separation of Concerns (SoC):** Strict multi-tier architecture isolating Controllers (DTO mapping), Services (Business Logic), and Repositories (Data Access).
* **Real-time Budget Validation:** In-memory aggregation of expenses using Java Stream API to validate budget limits before persisting new transactions to the database.
* **Strategy Pattern & JDBC:** The reporting module implements the **Strategy Design Pattern** (`ReportStrategy`) to dynamically select report types. It bypasses Hibernate in favor of raw, highly-optimized SQL queries via `JdbcTemplate` (utilizing `SUM`, `GROUP BY`, and `EXTRACT(MONTH)`) to maximize analytical performance.
* **Global Exception Handling:** A resilient API architecture powered by `@RestControllerAdvice`, intercepting business logic violations and mapping them into standardized, clean JSON `ErrorResponse` objects (preventing stack-trace leaks).
* **Data Transfer Objects (DTO):** Full separation between database entities and API payload, eliminating over-fetching and protecting sensitive entity structures.

## 🌐 API Endpoints
The API utilizes advanced Nested RESTful Routing for logical resource hierarchy:

| HTTP Method | Endpoint | Description |
| :--- | :--- | :--- |
| POST | /api/users | Register a new user |
| GET | /api/users/{userId} | Retrieve user profile details |
| POST | /api/users/{userId}/categories | Create a custom expense category |
| GET | /api/users/{userId}/categories | Fetch all categories for a specific user |
| POST | /api/users/{userId}/categories/{categoryId}/budgets | Set a budget limit for a specific category & month |
| POST | /api/users/{userId}/categories/{categoryId}/expenses | Log a new expense (triggers budget validation) |
| GET | /api/users/{userId}/expenses | Get a complete history of user's expenses |
| DELETE | /api/users/{userId}/expenses/{expenseId} | Remove an existing expense |
| GET | /api/users/{userId}/reports/{type} | Generate an analytical report (e.g., type=MONTHLY_CATEGORY?month=5&year=2026) |

## Author
**Mikołaj Jussak**
*Computer Science Student at Silesian University of Technology*
## ⚖️ License
Distributed under the **MIT License**. See [`LICENSE`](./LICENSE) for more information.