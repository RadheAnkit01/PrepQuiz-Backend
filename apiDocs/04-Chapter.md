# Chapter API

---

# Overview

The Chapter API is responsible for managing chapters within a subject.

A chapter belongs to exactly one subject, while a subject may contain multiple chapters.

Example

```
Subject
 ├── Introduction
 ├── Variables
 ├── Operators
 ├── Loops
 └── OOP
```

---

# Business Rules

- Every chapter must belong to an existing subject.
- Chapter names must be unique within the same subject.
- The same chapter name may exist in different subjects.
- Chapter cannot be deleted if one or more questions exist under it.
- Chapter name is mandatory.
- Only authorized users can create, update, or delete chapters.

---

# Endpoints

| Method | Endpoint | Description |
|----------|----------|-------------|
| POST | `/api/v1/chapters` | Create Chapter |
| GET | `/api/v1/chapters` | Get Chapters |
| GET | `/api/v1/chapters/{id}` | Get Chapter By Id |
| PUT | `/api/v1/chapters/{id}` | Update Chapter |
| DELETE | `/api/v1/chapters/{id}` | Delete Chapter |

---

# Create Chapter

## Endpoint

```
POST /api/v1/chapters
```

## Description

Creates a new chapter under an existing subject.

## Authorization

Required

---

## Request Body

```json
{
    "name": "Variables",
    "subjectId": 5
}
```

---

## Validation Rules

| Field | Rule |
|--------|------|
| name | Required |
| name | Maximum length as defined by application |
| subjectId | Required |
| subjectId | Must exist |
| name | Unique within selected subject |

---

## Success Response

**201 Created**

```json
{
    "id": 15,
    "name": "Variables",
    "subjectId": 5,
    "subjectName": "Java Programming",
    "createdAt": "2026-07-20T15:20:30Z",
    "updatedAt": "2026-07-20T15:20:30Z"
}
```

---

## Error Responses

### Subject Not Found

```json
{
    "status": 404,
    "message": "Subject not found."
}
```

---

### Duplicate Chapter

```json
{
    "status": 409,
    "message": "Chapter already exists in this subject."
}
```

---

### Validation Failed

```json
{
    "status": 400,
    "message": "Chapter name is required."
}
```

---

# Get Chapters

## Endpoint

```
GET /api/v1/chapters
```

## Description

Returns a paginated list of chapters.

Supports optional filtering by subject.

---

## Query Parameters

| Parameter | Type | Required | Description |
|------------|------|----------|-------------|
| subjectId | Long | No | Filter by subject |
| page | Integer | No | Default 0 |
| pageSize | Integer | No | Default 10 |
| sortBy | String | No | Default createdAt |
| direction | ASC/DESC | No | Default DESC |

---

## Example Requests

```
GET /chapters

GET /chapters?subjectId=5

GET /chapters?page=0&pageSize=20

GET /chapters?subjectId=5&direction=ASC
```

---

## Success Response

```json
{
    "content": [
        {
            "id": 15,
            "name": "Variables",
            "subjectId": 5,
            "subjectName": "Java Programming"
        },
        {
            "id": 16,
            "name": "Operators",
            "subjectId": 5,
            "subjectName": "Java Programming"
        }
    ],
    "page": 0,
    "pageSize": 10,
    "totalElements": 2,
    "totalPages": 1,
    "first": true,
    "last": true
}
```

---

# Get Chapter By Id

## Endpoint

```
GET /api/v1/chapters/{id}
```

---

## Path Parameters

| Parameter | Type | Description |
|------------|------|-------------|
| id | Long | Chapter Id |

---

## Success Response

```json
{
    "id": 15,
    "name": "Variables",
    "subjectId": 5,
    "subjectName": "Java Programming",
    "createdAt": "2026-07-20T15:20:30Z",
    "updatedAt": "2026-07-20T15:20:30Z"
}
```

---

## Error Response

```json
{
    "status": 404,
    "message": "Chapter not found."
}
```

---

# Update Chapter

## Endpoint

```
PUT /api/v1/chapters/{id}
```

## Description

Updates an existing chapter.

---

## Request Body

```json
{
    "name": "Variables and Data Types",
    "subjectId": 5
}
```

---

## Success Response

```json
{
    "id": 15,
    "name": "Variables and Data Types",
    "subjectId": 5,
    "subjectName": "Java Programming",
    "updatedAt": "2026-07-20T16:05:10Z"
}
```

---

## Business Rules

- Chapter must exist.
- Subject must exist.
- Chapter name must remain unique within the selected subject.

---

# Delete Chapter

## Endpoint

```
DELETE /api/v1/chapters/{id}
```

## Description

Deletes a chapter.

---

## Success Response

```
204 No Content
```

---

## Business Rules

Deletion is allowed only when:

- Chapter exists.
- No questions are mapped to the chapter.

Otherwise

```
409 Conflict
```

is returned.

---

# Entity Relationship

```
Course
   │
   └────────► Subject
                  │
                  └────────► Chapter
                                 │
                                 └────────► Question
```

---

# Workflow

```
Create Course

↓

Create Subject

↓

Create Chapter

↓

Add Questions

↓

Create Assessments
```

---

# Notes

- Chapters divide a subject into logical learning units.
- Chapter names are unique only within a subject.
- The same chapter name may exist in different subjects.
- Chapters act as the parent entity for questions.
- Deleting a chapter does not cascade delete questions.
- A chapter should be created before any question can be added.