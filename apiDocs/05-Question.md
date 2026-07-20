# Question API

---

# Overview

The Question API is responsible for managing questions within the Prep Quiz platform.

A question belongs to exactly one chapter and can later be used in one or more assessments.

Each question consists of:

- Question Statement
- Question Type
- Difficulty
- Status
- Chapter
- Multiple Options
- One or More Correct Answers

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
                                               │
                                               └────────► Question Options
```

---

# Business Rules

- Every question must belong to an existing chapter.
- Question text must be unique within the same chapter.
- Question must contain at least two options.
- Every question must have at least one correct option.
- Multiple correct options are allowed only for MULTIPLE_CHOICE questions.
- TRUE_FALSE questions must contain exactly two options.
- Only ACTIVE questions can be used in assessments.
- Questions referenced by published assessments cannot be deleted.

---

# Endpoints

| Method | Endpoint | Description |
|----------|----------|-------------|
| POST | `/api/v1/questions` | Create Question |
| POST | `/api/v1/questions/bulk` | Bulk Create Questions |
| GET | `/api/v1/questions` | Get Questions |
| GET | `/api/v1/questions/{id}` | Get Question By Id |
| PUT | `/api/v1/questions/{id}` | Update Question |
| DELETE | `/api/v1/questions/{id}` | Delete Question |

---

# Create Question

## Endpoint

```
POST /api/v1/questions
```

## Description

Creates a new question.

---

## Authorization

Required

---

## Request Body

```json
{
    "text": "Which keyword is used to inherit a class in Java?",
    "difficulty": "EASY",
    "questionType": "SINGLE_CHOICE",
    "status": "ACTIVE",
    "chapterId": 10,
    "options": [
        {
            "text": "extends",
            "correct": true
        },
        {
            "text": "implements",
            "correct": false
        },
        {
            "text": "inherits",
            "correct": false
        },
        {
            "text": "super",
            "correct": false
        }
    ]
}
```

---

## Validation Rules

| Field | Rule |
|--------|------|
| text | Required |
| text | Must be unique within chapter |
| chapterId | Required |
| chapterId | Must exist |
| difficulty | Required |
| questionType | Required |
| status | Required |
| options | Minimum 2 |
| options | Maximum as defined by application |
| correct option | At least one |

---

## Success Response

**201 Created**

```json
{
    "id": 51,
    "text": "Which keyword is used to inherit a class in Java?",
    "difficulty": "EASY",
    "questionType": "SINGLE_CHOICE",
    "status": "ACTIVE",
    "chapterId": 10,
    "createdAt": "...",
    "updatedAt": "..."
}
```

---

# Bulk Create Questions

## Endpoint

```
POST /api/v1/questions/bulk
```

## Description

Creates multiple questions within a single transaction.

If any question fails validation, the complete transaction is rolled back.

---

## Authorization

Required

---

## Request Body

```json
[
    {
        "text": "Question 1",
        "difficulty": "EASY",
        "questionType": "SINGLE_CHOICE",
        "status": "ACTIVE",
        "chapterId": 10,
        "options": [
            {
                "text": "A",
                "correct": true
            },
            {
                "text": "B",
                "correct": false
            }
        ]
    },
    {
        "text": "Question 2",
        "difficulty": "MEDIUM",
        "questionType": "TRUE_FALSE",
        "status": "ACTIVE",
        "chapterId": 10,
        "options": [
            {
                "text": "True",
                "correct": true
            },
            {
                "text": "False",
                "correct": false
            }
        ]
    }
]
```

---

## Success Response

```json
{
    "total": 2,
    "success": 2,
    "failed": 0,
    "questions": [
        {
            "id": 51,
            "text": "Question 1"
        },
        {
            "id": 52,
            "text": "Question 2"
        }
    ]
}
```

---

## Business Rules

- Maximum upload size should follow the application limit (for example, 500 questions).
- Entire request executes inside one transaction.
- Any validation failure rolls back the complete upload.

Example

```
100 Questions

↓

Question 73 Invalid

↓

Rollback Entire Upload
```

---

# Get Questions

## Endpoint

```
GET /api/v1/questions
```

---

## Description

Returns a paginated list of questions.

Supports filtering, pagination and sorting.

---

## Query Parameters

| Parameter | Type | Required | Description |
|------------|------|----------|-------------|
| scopeType | Enum | No | COURSE, SUBJECT, CHAPTER |
| scopeId | Long | No | Required with scopeType |
| difficulty | Enum | No | EASY, MEDIUM, HARD |
| questionType | Enum | No | SINGLE_CHOICE, MULTIPLE_CHOICE, TRUE_FALSE |
| status | Enum | No | ACTIVE, INACTIVE |
| page | Integer | No | Default 0 |
| pageSize | Integer | No | Default 10 |
| sortBy | String | No | Default createdAt |
| direction | ASC/DESC | No | Default DESC |

---

## Example Requests

```
GET /questions

GET /questions?difficulty=EASY

GET /questions?status=ACTIVE

GET /questions?questionType=SINGLE_CHOICE

GET /questions?scopeType=CHAPTER&scopeId=10

GET /questions?scopeType=SUBJECT&scopeId=3

GET /questions?scopeType=COURSE&scopeId=1

GET /questions?difficulty=MEDIUM&status=ACTIVE

GET /questions?page=0&pageSize=20
```

---

## Success Response

```json
{
    "content": [],
    "page": 0,
    "pageSize": 10,
    "totalElements": 120,
    "totalPages": 12,
    "first": true,
    "last": false
}
```

---

# Get Question By Id

## Endpoint

```
GET /api/v1/questions/{id}
```

---

## Path Parameters

| Parameter | Type | Description |
|------------|------|-------------|
| id | Long | Question Id |

---

## Success Response

```json
{
    "id": 51,
    "text": "Which keyword is used to inherit a class in Java?",
    "difficulty": "EASY",
    "questionType": "SINGLE_CHOICE",
    "status": "ACTIVE",
    "chapterId": 10,
    "options": [
        {
            "id": 1,
            "text": "extends",
            "correct": true
        },
        {
            "id": 2,
            "text": "implements",
            "correct": false
        }
    ]
}
```

---

# Update Question

## Endpoint

```
PUT /api/v1/questions/{id}
```

---

## Description

Updates an existing question.

---

## Business Rules

- Question must exist.
- Chapter must exist.
- Updated question text must remain unique within the chapter.
- Question type validation rules must remain satisfied.

---

## Success Response

```json
{
    "id": 51,
    "text": "Updated Question",
    "updatedAt": "2026-07-20T17:10:15Z"
}
```

---

# Delete Question

## Endpoint

```
DELETE /api/v1/questions/{id}
```

---

## Description

Deletes an existing question.

---

## Success Response

```
204 No Content
```

---

## Business Rules

Deletion is allowed only when:

- Question exists.
- Question is not referenced by any published assessment.

Otherwise,

```
409 Conflict
```

is returned.

---

# Question Types

| Type | Description |
|------|-------------|
| SINGLE_CHOICE | Exactly one correct option |
| MULTIPLE_CHOICE | One or more correct options |
| TRUE_FALSE | Exactly two options (True / False) |

---

# Difficulty Levels

| Value | Description |
|-------|-------------|
| EASY | Beginner |
| MEDIUM | Intermediate |
| HARD | Advanced |

---

# Question Status

| Status | Description |
|---------|-------------|
| ACTIVE | Available for assessments |
| INACTIVE | Hidden from assessments |

---

# Scope Types

| Scope | Description |
|--------|-------------|
| COURSE | Questions under all chapters of a course |
| SUBJECT | Questions under all chapters of a subject |
| CHAPTER | Questions under a specific chapter |

---

# Validation Summary

- Question text is required.
- Chapter must exist.
- Difficulty is mandatory.
- Question type is mandatory.
- Status is mandatory.
- Minimum two options are required.
- At least one option must be marked correct.
- Question text must be unique within the selected chapter.

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

Add Question to Assessment
```

---

# Notes

- Questions are the core building blocks of assessments.
- A question may belong to multiple assessments.
- Questions should remain reusable across assessments.
- Bulk upload is intended for large-scale content creation.
- Filtering APIs support efficient searching across the question bank.