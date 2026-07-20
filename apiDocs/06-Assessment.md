# Assessment API

---

# Overview

The Assessment API is responsible for creating and managing assessments within the Prep Quiz platform.

An assessment is a collection of questions that can be assigned to a Course, Subject, or Chapter.

Each assessment progresses through a defined lifecycle:

```
DRAFT
   │
   ▼
PUBLISHED
   │
   ▼
ARCHIVED
```

Only published assessments are available for students.

---

# Entity Relationship

```
Course / Subject / Chapter
             │
             ▼
       Assessment
             │
             ▼
   Assessment Question
             │
             ▼
         Question
```

---

# Business Rules

- Every assessment belongs to exactly one scope.
- Scope can be COURSE, SUBJECT or CHAPTER.
- Assessment title must be unique within the selected scope.
- Assessment must contain at least one question.
- Passing marks cannot exceed total marks.
- Total marks are calculated from assessment questions.
- Only ACTIVE questions can be added.
- Only DRAFT assessments can be updated.
- Only DRAFT assessments can be deleted.
- Only DRAFT assessments can be published.
- Only PUBLISHED assessments can be archived.

---

# Assessment Lifecycle

```
Create Assessment

↓

Status = DRAFT

↓

Update

↓

Publish

↓

Status = PUBLISHED

↓

Archive

↓

Status = ARCHIVED
```

---

# Endpoints

| Method | Endpoint | Description |
|----------|----------|-------------|
| POST | `/api/v1/assessments` | Create Assessment |
| GET | `/api/v1/assessments` | Get Assessments |
| GET | `/api/v1/assessments/{id}` | Get Assessment By Id |
| PUT | `/api/v1/assessments/{id}` | Update Assessment |
| DELETE | `/api/v1/assessments/{id}` | Delete Assessment |
| PATCH | `/api/v1/assessments/{id}/publish` | Publish Assessment |
| PATCH | `/api/v1/assessments/{id}/archive` | Archive Assessment |

---

# Create Assessment

## Endpoint

```
POST /api/v1/assessments
```

---

## Description

Creates a new assessment in DRAFT status.

---

## Authorization

Required

---

## Request Body

```json
{
  "title": "Java Basics Test",
  "description": "Assessment for Java Fundamentals",
  "scopeType": "CHAPTER",
  "scopeId": 10,
  "passingMarks": 15,
  "assessmentQuestions": [
    {
      "questionId": 101,
      "marks": 2,
      "negativeMarks": 0.5,
      "displayOrder": 1
    },
    {
      "questionId": 102,
      "marks": 2,
      "negativeMarks": 0,
      "displayOrder": 2
    }
  ]
}
```

---

## Validation Rules

| Field | Rule |
|--------|------|
| title | Required |
| title | Unique within scope |
| scopeType | Required |
| scopeId | Required |
| scope | Must exist |
| passingMarks | ≤ Total Marks |
| assessmentQuestions | Minimum 1 |
| questionId | Must exist |
| marks | Greater than 0 |
| displayOrder | Unique within assessment |

---

## Success Response

**201 Created**

```json
{
  "id": 20,
  "title": "Java Basics Test",
  "status": "DRAFT",
  "scopeType": "CHAPTER",
  "scopeId": 10,
  "totalMarks": 20,
  "passingMarks": 15,
  "createdAt": "...",
  "updatedAt": "..."
}
```

---

# Get Assessments

## Endpoint

```
GET /api/v1/assessments
```

---

## Description

Returns a paginated list of assessments.

Supports filtering, sorting and pagination.

---

## Query Parameters

| Parameter | Type | Required | Description |
|------------|------|----------|-------------|
| scopeType | Enum | No | COURSE, SUBJECT, CHAPTER |
| scopeId | Long | No | Scope Id |
| status | Enum | No | DRAFT, PUBLISHED, ARCHIVED |
| page | Integer | No | Default 0 |
| pageSize | Integer | No | Default 10 |
| sortBy | String | No | Default createdAt |
| direction | ASC/DESC | No | Default DESC |

---

## Example Requests

```
GET /assessments

GET /assessments?status=DRAFT

GET /assessments?status=PUBLISHED

GET /assessments?scopeType=COURSE&scopeId=1

GET /assessments?scopeType=SUBJECT&scopeId=5

GET /assessments?scopeType=CHAPTER&scopeId=10
```

---

## Success Response

```json
{
  "content": [],
  "page": 0,
  "pageSize": 10,
  "totalElements": 25,
  "totalPages": 3,
  "first": true,
  "last": false
}
```

---

# Get Assessment By Id

## Endpoint

```
GET /api/v1/assessments/{id}
```

---

## Path Parameters

| Parameter | Type | Description |
|------------|------|-------------|
| id | Long | Assessment Id |

---

## Success Response

```json
{
  "id": 20,
  "title": "Java Basics Test",
  "status": "DRAFT",
  "scopeType": "CHAPTER",
  "scopeId": 10,
  "totalMarks": 20,
  "passingMarks": 15,
  "assessmentQuestions": [
    {
      "questionId": 101,
      "marks": 2,
      "negativeMarks": 0.5,
      "displayOrder": 1
    }
  ]
}
```

---

## Error Response

```json
{
  "status": 404,
  "message": "Assessment not found."
}
```

---

# Update Assessment

## Endpoint

```
PUT /api/v1/assessments/{id}
```

---

## Description

Updates an existing assessment.

---

## Business Rules

- Assessment must exist.
- Assessment must be in DRAFT status.
- Scope must exist.
- Assessment title must remain unique.
- Passing marks cannot exceed total marks.
- Questions can be added, updated or removed.

---

## Success Response

```json
{
  "id": 20,
  "title": "Updated Java Test",
  "status": "DRAFT",
  "updatedAt": "2026-07-20T18:25:10Z"
}
```

---

# Delete Assessment

## Endpoint

```
DELETE /api/v1/assessments/{id}
```

---

## Description

Deletes an assessment.

---

## Success Response

```
204 No Content
```

---

## Business Rules

Deletion is allowed only when:

- Assessment exists.
- Status is DRAFT.

Otherwise,

```
409 Conflict
```

is returned.

---

# Publish Assessment

## Endpoint

```
PATCH /api/v1/assessments/{id}/publish
```

---

## Description

Publishes a draft assessment.

---

## Success Response

```json
{
  "id": 20,
  "status": "PUBLISHED"
}
```

---

## Business Rules

Publishing is allowed only when:

- Assessment exists.
- Status is DRAFT.
- Assessment contains at least one question.

Otherwise,

```
409 Conflict
```

is returned.

---

# Archive Assessment

## Endpoint

```
PATCH /api/v1/assessments/{id}/archive
```

---

## Description

Archives a published assessment.

---

## Success Response

```json
{
  "id": 20,
  "status": "ARCHIVED"
}
```

---

## Business Rules

Archiving is allowed only when:

- Assessment exists.
- Status is PUBLISHED.

Otherwise,

```
409 Conflict
```

is returned.

---

# Scope Types

| Scope | Description |
|--------|-------------|
| COURSE | Assessment belongs to a course |
| SUBJECT | Assessment belongs to a subject |
| CHAPTER | Assessment belongs to a chapter |

---

# Assessment Status

| Status | Description |
|---------|-------------|
| DRAFT | Editable |
| PUBLISHED | Available for students |
| ARCHIVED | Read-only historical record |

---

# Assessment Question

Each assessment question stores assessment-specific metadata.

| Field | Description |
|---------|-------------|
| questionId | Linked question |
| marks | Marks awarded |
| negativeMarks | Negative marking |
| displayOrder | Display sequence |

---

# Validation Summary

- Title is required.
- Scope must exist.
- Question must exist.
- Assessment requires at least one question.
- Passing marks cannot exceed total marks.
- Duplicate display order is not allowed.
- Duplicate questions are not allowed.
- Only ACTIVE questions should be added.

---

# Workflow

```
Create Assessment

↓

Draft

↓

Edit

↓

Publish

↓

Student Attempts

↓

Archive
```

---

# Notes

- Assessments are reusable evaluation units.
- Questions remain independent and may be shared across multiple assessments.
- Total marks are calculated automatically from assessment questions.
- Published assessments become read-only.
- Archived assessments are retained for audit and reporting purposes.