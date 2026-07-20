# 07-Common Responses

---

# Overview

This document defines the standard response formats used throughout the Prep Quiz API.

All API endpoints follow a consistent response structure to simplify client-side integration and improve maintainability.

---

# Success Responses

The API returns appropriate HTTP status codes along with JSON responses where applicable.

---

# 200 OK

Returned when a request completes successfully.

Example

```http
GET /api/v1/questions/10
```

Response

```json
{
    "id": 10,
    "text": "What is Java?",
    "difficulty": "EASY",
    "questionType": "SINGLE_CHOICE",
    "status": "ACTIVE"
}
```

---

# 201 Created

Returned after successfully creating a new resource.

Example

```http
POST /api/v1/questions
```

Response

```json
{
    "id": 51,
    "text": "What is Java?",
    "createdAt": "2026-07-20T16:20:15Z",
    "updatedAt": "2026-07-20T16:20:15Z"
}
```

---

# 204 No Content

Returned after successfully deleting a resource.

Example

```http
DELETE /api/v1/questions/51
```

Response Body

```
(No Content)
```

---

# Paginated Response

Collection endpoints return paginated data.

Example

```json
{
    "content": [
        {
            "id": 1,
            "name": "Java"
        }
    ],
    "page": 0,
    "pageSize": 10,
    "totalElements": 100,
    "totalPages": 10,
    "first": true,
    "last": false
}
```

---

# Bulk Operation Response

Used by bulk APIs.

Example

```json
{
    "total": 100,
    "success": 100,
    "failed": 0,
    "questions": [
        {
            "id": 1,
            "text": "Question 1"
        },
        {
            "id": 2,
            "text": "Question 2"
        }
    ]
}
```

---

# Error Responses

All API errors follow a common response structure.

```json
{
    "timestamp": "2026-07-20T17:30:25Z",
    "status": 400,
    "error": "Bad Request",
    "message": "Validation failed.",
    "path": "/api/v1/questions"
}
```

---

# Response Fields

| Field | Description |
|--------|-------------|
| timestamp | Time when the error occurred |
| status | HTTP status code |
| error | HTTP status name |
| message | Human-readable error message |
| path | Requested API path |

---

# Validation Error

Status

```
400 Bad Request
```

Example

```json
{
    "timestamp": "2026-07-20T17:30:25Z",
    "status": 400,
    "error": "Bad Request",
    "message": "Question text is required.",
    "path": "/api/v1/questions"
}
```

---

# Resource Not Found

Status

```
404 Not Found
```

Example

```json
{
    "timestamp": "2026-07-20T17:30:25Z",
    "status": 404,
    "error": "Not Found",
    "message": "Question not found.",
    "path": "/api/v1/questions/100"
}
```

---

# Duplicate Resource

Status

```
409 Conflict
```

Example

```json
{
    "timestamp": "2026-07-20T17:30:25Z",
    "status": 409,
    "error": "Conflict",
    "message": "Question already exists.",
    "path": "/api/v1/questions"
}
```

---

# Unauthorized

Status

```
401 Unauthorized
```

Example

```json
{
    "timestamp": "2026-07-20T17:30:25Z",
    "status": 401,
    "error": "Unauthorized",
    "message": "Authentication required.",
    "path": "/api/v1/questions"
}
```

---

# Forbidden

Status

```
403 Forbidden
```

Example

```json
{
    "timestamp": "2026-07-20T17:30:25Z",
    "status": 403,
    "error": "Forbidden",
    "message": "Access denied.",
    "path": "/api/v1/questions"
}
```

---

# Internal Server Error

Status

```
500 Internal Server Error
```

Example

```json
{
    "timestamp": "2026-07-20T17:30:25Z",
    "status": 500,
    "error": "Internal Server Error",
    "message": "Unexpected server error.",
    "path": "/api/v1/questions"
}
```

---

# Common HTTP Status Codes

| HTTP Status | Meaning | Usage |
|--------------|---------|-------|
| 200 | OK | Successful GET, PUT, PATCH |
| 201 | Created | Successful POST |
| 204 | No Content | Successful DELETE |
| 400 | Bad Request | Validation failure |
| 401 | Unauthorized | Authentication failed |
| 403 | Forbidden | Insufficient permissions |
| 404 | Not Found | Resource does not exist |
| 409 | Conflict | Duplicate resource or business rule violation |
| 500 | Internal Server Error | Unexpected server error |

---

# Pagination Response Fields

| Field | Description |
|--------|-------------|
| content | Resource list |
| page | Current page number |
| pageSize | Requested page size |
| totalElements | Total available records |
| totalPages | Total number of pages |
| first | Whether this is the first page |
| last | Whether this is the last page |

---

# API Design Principles

The Prep Quiz API follows these response principles:

- Consistent response structure across all modules.
- Appropriate HTTP status codes.
- Human-readable error messages.
- Predictable pagination format.
- Standard JSON naming conventions.
- Transactional consistency for write operations.

---

# Notes

- Every successful request returns the appropriate HTTP status code.
- Every failed request returns a structured error response.
- Clients should rely on HTTP status codes before inspecting the response body.
- Bulk operations return summary statistics along with created resources.