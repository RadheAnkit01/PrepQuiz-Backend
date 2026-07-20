# 08-Enums.md

# Enumerations Reference

---

# Overview

This document defines all enumerations used throughout the Prep Quiz API.

Enumerations ensure consistency across requests, responses, validation, and business logic.

Clients must send enum values exactly as documented.

Example

```json
{
    "difficulty": "MEDIUM"
}
```

Enum values are **case-sensitive**.

---

# Difficulty

Represents the difficulty level of a question.

## Values

| Value | Description |
|--------|-------------|
| EASY | Beginner level question |
| MEDIUM | Intermediate level question |
| HARD | Advanced level question |

---

## Example

```json
{
    "difficulty": "HARD"
}
```

---

# QuestionType

Represents the type of question.

## Values

| Value | Description |
|--------|-------------|
| SINGLE_CHOICE | Exactly one option must be correct |
| MULTIPLE_CHOICE | One or more options may be correct |
| TRUE_FALSE | True or False question |

---

## Business Rules

### SINGLE_CHOICE

- Minimum two options
- Exactly one correct option

Example

```json
{
    "questionType": "SINGLE_CHOICE"
}
```

---

### MULTIPLE_CHOICE

- Minimum two options
- One or more correct options

Example

```json
{
    "questionType": "MULTIPLE_CHOICE"
}
```

---

### TRUE_FALSE

- Exactly two options
- Normally "True" and "False"
- Exactly one correct option

Example

```json
{
    "questionType": "TRUE_FALSE"
}
```

---

# QuestionStatus

Represents the availability of a question.

## Values

| Value | Description |
|--------|-------------|
| ACTIVE | Available for assessments |
| INACTIVE | Hidden from assessments |

---

## Example

```json
{
    "status": "ACTIVE"
}
```

---

# QuestionScopeType

Determines the level used while filtering questions.

## Values

| Value | Description |
|--------|-------------|
| COURSE | Search questions across a course |
| SUBJECT | Search questions within a subject |
| CHAPTER | Search questions within a chapter |

---

## Example

```
GET /questions?scopeType=SUBJECT&scopeId=5
```

---

# AssessmentStatus

Represents the lifecycle state of an assessment.

## Values

| Value | Description |
|--------|-------------|
| DRAFT | Editable |
| PUBLISHED | Available to students |
| ARCHIVED | Read-only historical record |

---

## Lifecycle

```
DRAFT

↓

PUBLISHED

↓

ARCHIVED
```

---

## Business Rules

### DRAFT

Allowed

- Update
- Delete
- Publish

Not Allowed

- Archive

---

### PUBLISHED

Allowed

- Archive

Not Allowed

- Update
- Delete
- Publish

---

### ARCHIVED

Read-only

No further state transitions are allowed.

---

# AssessmentScopeType

Represents where an assessment belongs.

## Values

| Value | Description |
|--------|-------------|
| COURSE | Assessment belongs to a course |
| SUBJECT | Assessment belongs to a subject |
| CHAPTER | Assessment belongs to a chapter |

---

## Example

```json
{
    "scopeType": "CHAPTER",
    "scopeId": 10
}
```

---

# Sort Direction

Used by all paginated endpoints.

## Values

| Value | Description |
|--------|-------------|
| ASC | Ascending order |
| DESC | Descending order |

---

## Example

```
GET /questions?direction=DESC
```

---

# Common Usage

## Question Creation

```json
{
    "difficulty": "MEDIUM",
    "questionType": "SINGLE_CHOICE",
    "status": "ACTIVE"
}
```

---

## Question Search

```
GET /questions

?scopeType=COURSE

&scopeId=1

&difficulty=HARD

&status=ACTIVE

&questionType=MULTIPLE_CHOICE
```

---

## Assessment Creation

```json
{
    "scopeType": "SUBJECT",
    "scopeId": 5
}
```

---

# Invalid Enum Values

If an invalid enum value is supplied, the API returns:

```
400 Bad Request
```

Example

```
GET /questions?difficulty=MEDIU
```

Response

```json
{
    "status": 400,
    "error": "Bad Request",
    "message": "Invalid value 'MEDIU' for parameter 'difficulty'."
}
```

---

# Best Practices

- Always use uppercase enum values.
- Enum values are case-sensitive.
- Clients should validate enum values before making API requests.
- New enum values may be added in future API versions; clients should handle unknown values gracefully where appropriate.

---

# Summary

The current API defines the following enumerations:

| Enum | Values |
|------|--------|
| Difficulty | EASY, MEDIUM, HARD |
| QuestionType | SINGLE_CHOICE, MULTIPLE_CHOICE, TRUE_FALSE |
| QuestionStatus | ACTIVE, INACTIVE |
| QuestionScopeType | COURSE, SUBJECT, CHAPTER |
| AssessmentStatus | DRAFT, PUBLISHED, ARCHIVED |
| AssessmentScopeType | COURSE, SUBJECT, CHAPTER |
| Sort Direction | ASC, DESC |