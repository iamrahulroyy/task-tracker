# Task API (Spring Boot + PostgreSQL)

A RESTful backend service to manage tasks with status tracking, validation, and filtering.
Built to demonstrate backend fundamentals using raw SQL (no ORM).

---

## 🚀 Tech Stack

* Java 17
* Spring Boot (JDBC)
* PostgreSQL
* Maven
* Swagger (OpenAPI)

---

## 📌 Features

* Create Task (POST)
* Get All Tasks (GET)
* Get Task by ID (GET)
* Filter Tasks by Status (GET with query param)
* Input validation with proper error handling
* Global exception handling (400 / 404 responses)

---

## 📂 API Endpoints

### 🔹 Create Task

**POST /tasks**

Request:

```json
{
  "title": "Task name",
  "description": "Task details",
  "status": "PENDING"
}
```

---

### 🔹 Get All Tasks

**GET /tasks**

---

### 🔹 Get Task by ID

**GET /tasks/{id}**

---

### 🔹 Filter Tasks

**GET /tasks?status=PENDING**

Allowed values:

* PENDING
* IN_PROGRESS
* DONE

---

## ⚠️ Validation Rules

* `title` is required
* `status` must be one of:

  * PENDING
  * IN_PROGRESS
  * DONE

---

## 🧪 Swagger UI

After running the app:

👉 http://localhost:8080/swagger-ui/index.html

---

## ⚙️ Setup & Run

### 1. Create Database

```sql
CREATE DATABASE taskdb;
```

### 2. Configure Environment

Copy `.env.example` to a new file named `.env` and fill in your database credentials:

```bash
cp .env.example .env
```

**Note:** The `.env` file is ignored by Git for security.

---

### 3. Create Table

```sql
CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

### 4. Run Application

```bash
mvnw.cmd spring-boot:run
```

---

## 🧠 Design Decisions

* Used **JdbcTemplate** instead of ORM to demonstrate SQL handling
* Implemented **manual validation** for input control
* Used **enum-based validation** for task status
* Added **global exception handler** for clean API responses
* Implemented **filtering via query parameters**
* Used **Environment Variables** via `.env` for secure configuration management

---

## 📬 Notes

* Setup your `.env` file with database credentials before running
* Ensure PostgreSQL is running locally

---

## 👤 Author

Rahul Roy
