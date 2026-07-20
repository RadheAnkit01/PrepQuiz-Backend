# Subject API

---

# Overview

The Subject API is responsible for managing subjects within a course.

A subject belongs to exactly one course, while a course may contain multiple subjects.

Example

```
Course
 ├── Mathematics
 ├── Science
 ├── English
```

---

# Business Rules

- Every subject must belong to an existing course.
- Subject names must be unique within the same course.
- The same subject name may exist in different courses.
- Subject cannot be deleted if one or more chapters exist under it.
- Subject name is mandatory.
- Only authorized users can create, update, or delete subjects.

---

# Endpoints

| Method | Endpoint | Description |
|----------|----------|-------------|
| POST | `/api/v1/subjects` | Create Subject |
| GET | `/api/v1/subjects` | Get Subjects |
| GET | `/api/v1/subjects/{id}` | Get Subject By Id |
| PUT | `/api/v1/subjects/{id}` | Update Subject |
| DELETE | `/api/v1/subjects/{id}` | Delete Subject |

---

# Create Subject

## Endpoint

```
POST /api/v1/subjects
```

## Description

Creates a new subject under an existing course.

## Authorization

Required

## Request Body

```json
{
    "name": "Mathematics",
    "courseId": 1
}
```

## Validation Rules

| Field | Rule |
|--------|------|
| name | Required |
| name | Maximum length as defined by application |
| courseId | Required |
| courseId | Must exist |
| name | Unique within selected course |

## Success Response

**201 Created**

```json
{
    "id": 10,
    "name": "Mathematics",
    "courseId": 1,
    "courseName": "Class 10",
    "createdAt": "2026-07-20T14:20:15Z",
    "updatedAt": "2026-07-20T14:20:15Z"
}
```

## Error Responses

### Course Not Found

```json
{
    "status": 404,
    "message": "Course not found."
}
```

### Duplicate Subject

```json
{
    "status": 409,
    "message": "Subject already exists in this course."
}
```

### Validation Failed

```json
{
    "status": 400,
    "message": "Subject name is required."
}
```

---

# Get Subjects

## Endpoint

```
GET /api/v1/subjects
```

## Description

Returns a paginated list of subjects.

Supports optional filtering by course.

---

## Query Parameters

| Parameter | Type | Required | Description |
|------------|------|----------|-------------|
| courseId | Long | No | Filter by course |
| page | Integer | No | Default 0 |
| pageSize | Integer | No | Default 10 |
| sortBy | String | No | Default createdAt |
| direction | ASC/DESC | No | Default DESC |

---

## Example Requests

```
GET /subjects

GET /subjects?courseId=1

GET /subjects?page=0&pageSize=20

GET /subjects?courseId=1&direction=ASC
```

---

## Success Response

```json
{
    "content": [
        {
            "id": 10,
            "name": "Mathematics",
            "courseId": 1,
            "courseName": "Class 10"
        },
        {
            "id": 11,
            "name": "Science",
            "courseId": 1,
            "courseName": "Class 10"
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

# Get Subject By Id

## Endpoint

```
GET /api/v1/subjects/{id}
```

## Path Parameters

| Parameter | Type | Description |
|------------|------|-------------|
| id | Long | Subject Id |

---

## Success Response

```json
{
    "id": 10,
    "name": "Mathematics",
    "courseId": 1,
    "courseName": "Class 10",
    "createdAt": "2026-07-20T14:20:15Z",
    "updatedAt": "2026-07-20T14:20:15Z"
}
```

---

## Error Response

```json
{
    "status": 404,
    "message": "Subject not found."
}
```

---

# Update Subject

## Endpoint

```
PUT /api/v1/subjects/{id}
```

## Description

Updates an existing subject.

---

## Request Body

```json
{
    "name": "Advanced Mathematics",
    "courseId": 1
}
```

---

## Success Response

```json
{
    "id": 10,
    "name": "Advanced Mathematics",
    "courseId": 1,
    "courseName": "Class 10",
    "updatedAt": "2026-07-20T15:10:40Z"
}
```

---

## Business Rules

- Subject must exist.
- Course must exist.
- Subject name must remain unique within the selected course.

---

# Delete Subject

## Endpoint

```
DELETE /api/v1/subjects/{id}
```

## Description

Deletes a subject.

---

## Success Response

```
204 No Content
```

---

## Business Rules

Deletion is allowed only when:

- Subject exists.
- No chapters are mapped to the subject.

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
   └────────────► Subject
                     │
                     └────────────► Chapter
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

Create Question

↓

Create Assessment
```

---

# Notes

- Subjects organize academic content within a course.
- Subject names are unique only inside a course.
- The same subject name may exist across different courses.
- Subjects act as the parent entity for chapters.
- Deleting a subject does not cascade delete chapters.