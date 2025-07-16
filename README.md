# Course Search Application

A Spring Boot application that indexes and searches course documents using Elasticsearch. The project includes Dockerized Elasticsearch and the application, with REST APIs documented via Swagger/OpenAPI.

---

## Technology Stack

- Java 17
- Spring Boot 3.5.x
- Spring Data Elasticsearch 5.5.x
- Elasticsearch 8.x (Docker)
- Docker & Docker Compose
- Swagger / OpenAPI for API documentation and testing

---

## Features

- Bulk indexes sample course data into Elasticsearch on startup
- Search courses with filters:
  - Full-text search on title and description
  - Filters by age range, category, type, price range, and session date
  - Sorting by next session date or price
  - Pagination support
  - Autocomplete and fuzzy search(to implement)
- REST API documented and testable via Swagger UI
- Integration Testing

---

## Getting Started

### Prerequisites

- Docker & Docker Compose installed
- Java 17 JDK installed (for local build if needed)
- Maven (optional if you use the provided Docker setup)

### Setup and Run with Docker

1. **Clone the repository:**

   ```bash
   https://github.com/Amit-Yadav316/Assignment-Spring-boot.git
   cd course-search

2. **Start Elasticsearch and the Spring Boot application using Docker Compose:**
   ```bash
   docker-compose up -d

3. **Verify Elasticsearch is running:**
   [Open]http://localhost:9200
   You should see Elasticsearch cluster info JSON

4. **Application Start**
   [Open]http://localhost:8080
   You should see Welcome Message

5. **API Documentation**
   1. Swagger UI is available to explore and test the REST endpoints:
   [Open]http://localhost:8080/swagger-ui.html

   2. You would see this page

   <img width="1898" height="985" alt="Screenshot 2025-07-16 160629" src="https://github.com/user-attachments/assets/b13f7e17-3489-49c7-8261-590699a7f33c" />


   3. Click where I am pointing 

   <img width="1898" height="985" alt="image" src="https://github.com/user-attachments/assets/466ecd7d-b658-403d-9d02-c2c05f1289b7" />



   4. Now click on "Try it Out"

   <img width="1892" height="969" alt="image" src="https://github.com/user-attachments/assets/39d3a7fa-fd97-4d94-81d4-126947f702f5" />


   5. Fill Your Desired Field and Execute

   <img width="1894" height="980" alt="image" src="https://github.com/user-attachments/assets/13acf5a1-e336-4f67-ac23-b7db0f1e2c29" />



6. **Testing**
   ```bash
   ./mvnw test
   
