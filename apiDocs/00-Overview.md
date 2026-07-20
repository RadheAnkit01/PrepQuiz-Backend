# 00-Overview.md

# Prep Quiz API Documentation

**Version:** v1.0.0  
**API Version:** v1  
**Status:** Stable  
**Base URL:** `/api/v1`

---

# Overview

The Prep Quiz API provides RESTful endpoints for managing educational content including courses, subjects, chapters, questions, and assessments.

The API is designed for coaching institutes, schools, and educational organizations to build and manage online assessments.

All APIs communicate using JSON over HTTPS and follow REST principles.

---

# API Versioning

Current Version

```
v1
```

Base URL

```
/api/v1
```

Future versions will use:

```
/api/v2
```

without breaking existing clients.

---

# Authentication

Most endpoints require authentication using JWT Bearer Token.

Example

```
Authorization: Bearer <access_token>
```

Endpoints like Login do not require authentication.

---

# Request Content Type

```
Content-Type: application/json
```

---

# Response Content Type

```
application/json
```

---

# Date & Time Format

All date and time fields are returned using ISO-8601 format.

Example

```json
{
  "createdAt": "2026-07-20T15:42:18.124Z",
  "updatedAt": "2026-07-20T15:55:41.672Z"
}
```

All timestamps are stored in UTC.

---

# HTTP Methods

| Method | Description |
|----------|-------------|
| GET | Retrieve resource |
| POST | Create resource |
| PUT | Update resource |
| PATCH | Partial update |
| DELETE | Delete resource |

---

# HTTP Status Codes

| Status | Meaning |
|---------|----------|
|200|Success|
|201|Created|
|204|No Content|
|400|Bad Request|
|401|Unauthorized|
|403|Forbidden|
|404|Not Found|
|409|Conflict|
|500|Internal Server Error|

---

# Pagination

Endpoints returning collections support pagination.

Example

```
GET /questions?page=0&pageSize=20
```

Default values

| Property | Value |
|-----------|-------|
|page|0|
|pageSize|10|

---

# Sorting

Collection endpoints support sorting.

Example

```
GET /questions?sortBy=createdAt&direction=DESC
```

Default values

| Property | Value |
|-----------|-------|
|sortBy|createdAt|
|direction|DESC|

---

# Filtering

Many endpoints support optional filtering.

Example

```
GET /questions?difficulty=EASY

GET /questions?scopeType=CHAPTER&scopeId=10

GET /questions?status=ACTIVE
```

Multiple filters may be combined.

---

# Standard Success Response

Single Resource

```json
{
  "id": 1,
  "name": "Java Basics"
}
```

Collection

```json
{
  "content": [],
  "page": 0,
  "pageSize": 10,
  "totalElements": 100,
  "totalPages": 10,
  "first": true,
  "last": false
}
```

---

# Standard Error Response

```json
{
  "timestamp": "2026-07-20T16:20:11.672Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed.",
  "path": "/api/v1/questions"
}
```

---

# Validation

Input validation is performed before business logic execution.

Examples include

- Required fields
- Enum validation
- Duplicate resource validation
- Relationship validation
- Business rule validation

Validation failures return

```
400 Bad Request
```

---

# Transaction Management

Write operations are transactional.

If any validation or persistence step fails, the entire transaction is rolled back.

Example

```
Bulk Question Upload

100 Questions

↓

Question 48 Invalid

↓

Rollback Entire Transaction
```

---

# Business Rule Validation

The API validates business rules in addition to field validation.

Examples

- Duplicate course names
- Duplicate subject names within a course
- Duplicate chapter names within a subject
- Duplicate question text within a chapter
- Assessment passing marks cannot exceed total marks
- Only Draft assessments may be edited

---

# Naming Conventions

## URL

```
/courses

/subjects

/chapters

/questions

/assessments
```

Plural nouns are used.

---

## JSON

All JSON properties use camelCase.

Example

```json
{
  "questionType": "SINGLE_CHOICE",
  "passingMarks": 25
}
```

---

# API Modules

Current Version includes

- Authentication
- Course Management
- Subject Management
- Chapter Management
- Question Management
- Bulk Question Upload
- Assessment Management

Future Modules

- Student Attempt
- Result Processing
- Analytics
- Institute Management
- User Management
- Reports
- Notifications

---

# API Design Principles

The Prep Quiz API follows these principles:

- RESTful architecture
- Stateless communication
- Consistent response structure
- Proper HTTP status codes
- Transactional write operations
- Server-side validation
- Predictable endpoint naming
- Secure authentication using JWT

---

# Changelog

See `CHANGELOG.md` for version history.