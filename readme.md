# Task API (Spring Boot + PostgreSQL)

A RESTful backend service to manage tasks with status tracking, validation, and filtering.
This project was built as part of an assignment to demonstrate backend fundamentals using Spring Boot and raw SQL (JdbcTemplate).

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
* Get All Tasks (GET) with pagination
* Get Task by ID (GET)
* Filter Tasks by Status (GET with query param + pagination)
* Update Task (PUT)
* Delete Task (DELETE)
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

Supports pagination and optional status filter:

```
GET /tasks
GET /tasks?status=PENDING
GET /tasks?page=0&size=10
GET /tasks?status=IN_PROGRESS&page=1&size=5
```

| Param | Required | Default | Description |
|---|---|---|---|
| `status` | No | — | Filter by `PENDING`, `IN_PROGRESS`, or `DONE` |
| `page` | No | `0` | Page number (0-indexed) |
| `size` | No | `10` | Results per page |

---

### 🔹 Get Task by ID

**GET /tasks/{id}**

---

### 🔹 Update Task

**PUT /tasks/{id}**

Request body (same shape as POST):

```json
{
  "title": "Updated title",
  "description": "Updated details",
  "status": "IN_PROGRESS"
}
```

> `status` is optional on update — if not sent, the existing status is kept.

---

### 🔹 Delete Task

**DELETE /tasks/{id}**

Returns `204 No Content` on success.

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

Copy `.env.example` to `.env` and fill in your database credentials:

```bash
cp .env.example .env
```

**Note:** The `.env` file is ignored by Git so credentials don't get pushed.

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

* Used **JdbcTemplate** instead of JPA/ORM — I wanted to write raw SQL and understand what's actually happening at the DB level
* Kept **manual validation** in the service layer — easier to control what error message goes back to the client
* Used an **enum for task status** so invalid values get caught with a clear 400 response instead of inserting garbage into the DB
* Added a **global exception handler** so error responses are always the same JSON format
* Used a `.env` file for DB credentials instead of hardcoding them in `application.properties`

This project focuses on simplicity and correctness over over-engineering, keeping the code easy to understand and explain.

---

## 📬 Notes

* Make sure PostgreSQL is running before starting the app
* Setup the `.env` file first — app won't start without DB credentials

---

## 👤 Author

Rahul Roy
