# 09-Business Rules

# Business Rules

---

# Overview

This document defines the business rules enforced by the Prep Quiz platform.

Business rules ensure data consistency, maintain referential integrity, and enforce platform workflows beyond basic field validation.

Unlike input validation (such as required fields or string length), business rules validate relationships, state transitions, uniqueness, and lifecycle constraints.

---

# Course Rules

## Creation

- Course name is required.
- Course name must be unique.
- Course names are case-insensitive.
- Leading and trailing whitespace should be ignored.

---

## Update

- Course must exist.
- Updated course name must remain unique.

---

## Delete

A course can be deleted only if:

- No subjects exist under the course.

Otherwise,

```
409 Conflict
```

is returned.

---

# Subject Rules

## Creation

- Subject must belong to an existing course.
- Subject name is required.
- Subject name must be unique within the selected course.

---

## Update

- Subject must exist.
- Target course must exist.
- Updated subject name must remain unique within the selected course.

---

## Delete

A subject can be deleted only if:

- No chapters exist under the subject.

---

# Chapter Rules

## Creation

- Chapter must belong to an existing subject.
- Chapter name is required.
- Chapter name must be unique within the selected subject.

---

## Update

- Chapter must exist.
- Updated chapter name must remain unique within the selected subject.

---

## Delete

A chapter can be deleted only if:

- No questions exist under the chapter.

---

# Question Rules

## Creation

Every question must:

- Belong to an existing chapter.
- Have unique text within the chapter.
- Have a valid difficulty.
- Have a valid question type.
- Have a valid status.
- Contain at least two options.
- Contain at least one correct option.

---

## Question Type Rules

### SINGLE_CHOICE

- Exactly one correct option.

---

### MULTIPLE_CHOICE

- One or more correct options.

---

### TRUE_FALSE

- Exactly two options.
- Exactly one correct option.

---

## Update

Question must exist.

Updated question text must remain unique within the chapter.

---

## Delete

Question can be deleted only if it is not referenced by any published assessment.

---

## Bulk Upload

Bulk upload follows an **all-or-nothing** transaction model.

Example

```
100 Questions

↓

Question 57 Invalid

↓

Entire Upload Rolled Back
```

Rules

- Every question must pass validation.
- Any failure rolls back the complete transaction.
- No partial inserts.

---

# Assessment Rules

## Creation

Assessment must:

- Belong to a valid scope.
- Have a unique title within the selected scope.
- Contain at least one question.
- Contain only valid questions.
- Have passing marks less than or equal to total marks.

---

## Assessment Question Rules

Each assessment question must contain:

- Valid Question
- Marks
- Display Order

Rules

- Display order must be unique.
- Duplicate questions are not allowed.
- Marks must be greater than zero.
- Negative marks cannot exceed awarded marks.

---

# Assessment Lifecycle

```
DRAFT

↓

PUBLISHED

↓

ARCHIVED
```

---

## DRAFT

Allowed

- Update
- Delete
- Publish

Not Allowed

- Archive

---

## PUBLISHED

Allowed

- Archive

Not Allowed

- Update
- Delete
- Publish

---

## ARCHIVED

Read-only state.

No further modifications are allowed.

---

# Scope Rules

Both Questions and Assessments support scopes.

Available scopes

- COURSE
- SUBJECT
- CHAPTER

Rules

- Scope must exist.
- Scope type and scope id must always represent the same entity.

Example

```
scopeType = COURSE

scopeId = Existing Course Id
```

---

# Referential Integrity

The following parent-child relationships are enforced.

```
Course

↓

Subject

↓

Chapter

↓

Question

↓

Assessment Question

↓

Assessment
```

Parent records cannot be removed while dependent records exist unless explicitly supported by the application.

---

# Pagination Rules

Collection APIs

- Default page = 0
- Default pageSize = 10

Clients may specify:

- page
- pageSize
- sortBy
- direction

---

# Sorting Rules

Default sorting

```
createdAt DESC
```

Supported directions

- ASC
- DESC

---

# Filtering Rules

Questions support

- Scope
- Difficulty
- Question Type
- Status

Assessments support

- Scope
- Status

Filters may be combined.

Example

```
GET /questions

?scopeType=COURSE

&scopeId=1

&difficulty=MEDIUM

&status=ACTIVE
```

---

# Auditing Rules

Every entity maintains auditing information.

Fields

- createdAt
- updatedAt

Rules

- createdAt is immutable.
- updatedAt changes after every successful update.

---

# Transaction Rules

All write operations execute inside a transaction.

Operations include

- Create
- Update
- Delete
- Publish
- Archive
- Bulk Upload

Any failure causes rollback.

---

# Validation Order

The platform validates requests in the following sequence.

```
Request

↓

DTO Validation

↓

Business Validation

↓

Relationship Validation

↓

Persistence

↓

Response
```

This order minimizes unnecessary database operations and ensures consistent error reporting.

---

# Future Business Rules

The following rules are planned for future releases.

## Student Attempt

- Student can attempt only published assessments.
- Assessment availability window must be respected.
- Multiple attempts depend on assessment configuration.

---

## Institute

- Data isolation between institutes.
- Feature-based access control.
- Subscription-based module access.

---

## Summary

The Prep Quiz platform enforces business rules to ensure:

- Data consistency
- Referential integrity
- Predictable workflows
- Secure state transitions
- Transactional reliability
- Reusable academic content