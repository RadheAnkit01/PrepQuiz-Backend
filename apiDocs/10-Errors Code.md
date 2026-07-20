# 10-Error Codes

# Error Codes Reference

---

# Overview

This document defines all standard HTTP status codes and application-specific error codes returned by the Prep Quiz API.

The objective is to provide predictable, machine-readable errors while keeping messages understandable for developers and API consumers.

---

# Standard Error Response

Every API error follows the same response structure.

```json
{
    "timestamp": "2026-07-20T18:45:15Z",
    "status": 404,
    "error": "Not Found",
    "code": "QUESTION_NOT_FOUND",
    "message": "Question not found with id: 15.",
    "path": "/api/v1/questions/15"
}
```

---

# Error Response Fields

| Field | Description |
|---------|-------------|
| timestamp | Time when the error occurred |
| status | HTTP status code |
| error | HTTP status description |
| code | Application-specific error code |
| message | Human-readable message |
| path | Requested API endpoint |

---

# HTTP Status Codes

| Status | Meaning |
|---------|----------|
|200|OK|
|201|Created|
|204|No Content|
|400|Bad Request|
|401|Unauthorized|
|403|Forbidden|
|404|Not Found|
|409|Conflict|
|500|Internal Server Error|

---

# Validation Errors

## INVALID_REQUEST

HTTP Status

```
400 Bad Request
```

Example

```json
{
    "code": "INVALID_REQUEST",
    "message": "Request validation failed."
}
```

---

## INVALID_ENUM_VALUE

```
400 Bad Request
```

Example

```json
{
    "code": "INVALID_ENUM_VALUE",
    "message": "Invalid value 'MEDIU' for parameter 'difficulty'."
}
```

---

## INVALID_SORT_DIRECTION

```
400 Bad Request
```

Example

```json
{
    "code": "INVALID_SORT_DIRECTION",
    "message": "Invalid sort direction."
}
```

---

## INVALID_SCOPE

```
400 Bad Request
```

Example

```json
{
    "code": "INVALID_SCOPE",
    "message": "Invalid scope type."
}
```

---

# Authentication Errors

## UNAUTHORIZED

```
401 Unauthorized
```

```json
{
    "code": "UNAUTHORIZED",
    "message": "Authentication required."
}
```

---

## INVALID_TOKEN

```
401 Unauthorized
```

```json
{
    "code": "INVALID_TOKEN",
    "message": "Invalid access token."
}
```

---

## TOKEN_EXPIRED

```
401 Unauthorized
```

```json
{
    "code": "TOKEN_EXPIRED",
    "message": "Access token has expired."
}
```

---

## ACCESS_DENIED

```
403 Forbidden
```

```json
{
    "code": "ACCESS_DENIED",
    "message": "Access denied."
}
```

---

# Course Errors

## COURSE_NOT_FOUND

```
404 Not Found
```

---

## DUPLICATE_COURSE

```
409 Conflict
```

---

## COURSE_DELETE_NOT_ALLOWED

```
409 Conflict
```

Returned when the course contains one or more subjects.

---

# Subject Errors

## SUBJECT_NOT_FOUND

```
404 Not Found
```

---

## DUPLICATE_SUBJECT

```
409 Conflict
```

---

## SUBJECT_DELETE_NOT_ALLOWED

```
409 Conflict
```

Returned when chapters exist under the subject.

---

# Chapter Errors

## CHAPTER_NOT_FOUND

```
404 Not Found
```

---

## DUPLICATE_CHAPTER

```
409 Conflict
```

---

## CHAPTER_DELETE_NOT_ALLOWED

```
409 Conflict
```

Returned when questions exist under the chapter.

---

# Question Errors

## QUESTION_NOT_FOUND

```
404 Not Found
```

---

## DUPLICATE_QUESTION

```
409 Conflict
```

---

## INVALID_QUESTION_OPTIONS

```
400 Bad Request
```

Examples

- Less than two options
- No correct option
- Multiple correct options for SINGLE_CHOICE

---

## INVALID_QUESTION_TYPE

```
400 Bad Request
```

---

## QUESTION_DELETE_NOT_ALLOWED

```
409 Conflict
```

Returned when the question is referenced by a published assessment.

---

## BULK_UPLOAD_FAILED

```
400 Bad Request
```

Returned when any question fails validation during bulk upload.

Since bulk upload is transactional, no questions are saved.

---

# Assessment Errors

## ASSESSMENT_NOT_FOUND

```
404 Not Found
```

---

## DUPLICATE_ASSESSMENT

```
409 Conflict
```

---

## INVALID_PASSING_MARKS

```
400 Bad Request
```

Returned when passing marks exceed total marks.

---

## EMPTY_ASSESSMENT

```
400 Bad Request
```

Returned when no questions are supplied.

---

## INVALID_ASSESSMENT_STATE

```
409 Conflict
```

Returned when an operation is not allowed for the current assessment status.

Examples

- Update Published Assessment
- Delete Published Assessment
- Publish Archived Assessment
- Archive Draft Assessment

---

## ASSESSMENT_ALREADY_PUBLISHED

```
409 Conflict
```

---

## ASSESSMENT_ALREADY_ARCHIVED

```
409 Conflict
```

---

# Server Errors

## INTERNAL_SERVER_ERROR

```
500 Internal Server Error
```

Unexpected application failure.

---

## DATABASE_ERROR

```
500 Internal Server Error
```

Unexpected persistence failure.

---

# Error Handling Guidelines

Clients should:

- Check the HTTP status code first.
- Read the application error code.
- Display the message when appropriate.
- Avoid relying on exact message text for business logic.
- Use the `code` field for programmatic handling.

---

# Exception Mapping

| Exception | HTTP Status | Error Code |
|------------|-------------|------------|
| ValidationException | 400 | INVALID_REQUEST |
| MethodArgumentTypeMismatchException | 400 | INVALID_ENUM_VALUE |
| EntityNotFoundException | 404 | *_NOT_FOUND |
| DuplicateResourceException | 409 | DUPLICATE_* |
| BusinessValidationException | 409 | Business-specific code |
| AccessDeniedException | 403 | ACCESS_DENIED |
| AuthenticationException | 401 | UNAUTHORIZED |
| Exception | 500 | INTERNAL_SERVER_ERROR |

---

# Best Practices

- Every custom exception should expose a unique error code.
- Error codes should remain stable across API versions.
- Messages may evolve to improve clarity, but codes should not change.
- Frontend applications should rely on the `code` field for handling specific scenarios.

---

# Summary

The Prep Quiz API follows a standardized error-handling strategy based on:

- Consistent HTTP status codes
- Stable application error codes
- Structured JSON responses
- Predictable exception mapping
- Clear, actionable error messages

This approach simplifies debugging, improves client integration, and provides a consistent developer experience.