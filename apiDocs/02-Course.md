# Course API

---

# Overview

The Course API is responsible for managing courses within the Prep Quiz platform.

A course is the top-level academic entity. Each course may contain one or more subjects.

Example:

```
Course
 ├── Mathematics
 ├── Physics
 ├── Chemistry
```

---

# Business Rules

- Course name must be unique.
- Course names are case-insensitive.
- Course cannot be deleted if it contains one or more subjects.
- Course name is mandatory.
- Only authorized users can create, update, or delete courses.

---

# Endpoints

| Method | Endpoint | Description |
|----------|----------|-------------|
| POST | `/api/v1/courses` | Create Course |
| GET | `/api/v1/courses` | Get All Courses |
| GET | `/api/v1/courses/{id}` | Get Course By Id |
| PUT | `/api/v1/courses/{id}` | Update Course |
| DELETE | `/api/v1/courses/{id}` | Delete Course |

---

# Create Course

## Endpoint

```
POST /api/v1/courses
```

## Description

Creates a new course.

## Authorization

Required

## Request Body

```json
{
  "name": "Class 10"
}
```

## Validation Rules

| Field | Rule |
|--------|------|
| name | Required |
| name | Maximum length as defined by the application |
| name | Must be unique |

## Success Response

**201 Created**

```json
{
  "id": 1,
  "name": "Class 10",
  "createdAt": "2026-07-20T12:20:15Z",
  "updatedAt": "2026-07-20T12:20:15Z"
}
```

## Error Responses

### Duplicate Course

```json
{
  "status": 409,
  "message": "Course already exists."
}
```

### Validation Error

```json
{
  "status": 400,
  "message": "Course name is required."
}
```

---

# Get All Courses

## Endpoint

```
GET /api/v1/courses
```

## Description

Returns all available courses.

## Authorization

Required

## Success Response

**200 OK**

```json
[
  {
    "id": 1,
    "name": "Class 10"
  },
  {
    "id": 2,
    "name": "Class 11"
  }
]
```

---

# Get Course By Id

## Endpoint

```
GET /api/v1/courses/{id}
```

## Path Parameters

| Parameter | Type | Description |
|------------|------|-------------|
| id | Long | Course Id |

## Success Response

```json
{
  "id": 1,
  "name": "Class 10"
}
```

## Error Response

```json
{
  "status": 404,
  "message": "Course not found."
}
```

---

# Update Course

## Endpoint

```
PUT /api/v1/courses/{id}
```

## Description

Updates an existing course.

## Request Body

```json
{
  "name": "Class X"
}
```

## Success Response

```json
{
  "id": 1,
  "name": "Class X",
  "updatedAt": "2026-07-20T13:40:25Z"
}
```

## Business Rules

- Course must exist.
- New name must be unique.

---

# Delete Course

## Endpoint

```
DELETE /api/v1/courses/{id}
```

## Description

Deletes an existing course.

## Success Response

```
204 No Content
```

## Business Rules

Deletion is allowed only when:

- Course exists.
- No subjects are mapped to the course.

Otherwise,

```
409 Conflict
```

will be returned.

---

# Entity Relationship

```
Course
   │
   └──────────────► Subject (1:N)
```

---

# Workflow

```
Create Course

↓

Create Subjects

↓

Create Chapters

↓

Create Questions

↓

Create Assessments
```

---

# Notes

- Course is the root academic entity.
- Course names should be meaningful.
- Deleting a course may impact downstream academic data and is therefore restricted.