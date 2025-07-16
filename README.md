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
   git clone https://github.com/<your-github-username>/course-search.git
   # Replace <your-github-username> with your actual GitHub account name
   cd course-search

2. **Start Elasticsearch and the Spring Boot application using Docker Compose:**
   ```bash
   docker-compose up -d

3. **Verify Elasticsearch is running:**
   [Open]http://localhost:9200
   You should see Elasticsearch cluster info JSON

4. **Application Start**
   [Open](http://localhost:8080)


5. **API Documentation**
   Swagger UI is available to explore and test the REST endpoints:
   [Open]http://localhost:8080/swagger-ui.html

6. **Testing**
   ```bash
   ./mvnw test

   
   
