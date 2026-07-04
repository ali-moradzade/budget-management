# Category Service - Black Box Testing

## 1. Overview

This document defines black-box test cases for CategoryService.

Testing techniques used:

- Decision Table Testing
- Equivalence Partitioning

The focus is on external behavior via service/API, not implementation details.

---

## 2. Functionality Under Test

CategoryService provides:

1. Create Category
2. Get User Categories

---

## 3. Decision Table Testing

### 3.1 Create Category

| User Valid | Category Name Valid | Category Already Exists | Expected Result          |
|------------|---------------------|-------------------------|--------------------------|
| ✔          | ✔                   | ✘                       | Category created         |
| ✘          | ✔                   | ✘                       | User not found error     |
| ✔          | ✔                   | ✔                       | Duplicate category error |
| ✔          | ✘ (null/empty)      | -                       | Validation error         |

---

## 4. Equivalence Partitioning

### 4.1 User ID

- Valid: existing user in DB
- Invalid: non-existing user → ResourceNotFoundException

---

### 4.2 Category Name

- Valid:
    - Non-null string
    - Example: "Food", "Transport"

- Invalid:
    - null
    - empty string ""

- Duplicate:
    - same name already exists for user

---

## 5. Test Cases

### TC-C1: Successfully create category

- Input:
    - userId: valid
    - categoryName: "Food"
- Expected:
    - Category saved successfully

---

### TC-C2: Create category with invalid user

- Input:
    - userId: invalid
- Expected:
    - ResourceNotFoundException

---

### TC-C3: Duplicate category creation

- Input:
    - userId: valid
    - categoryName: existing category
- Expected:
    - ResourceAlreadyExistsException

---

### TC-C4: Invalid category name

- Input:
    - categoryName: null or ""
- Expected:
    - Validation error (400 Bad Request)

---

## 6. Get User Categories

### TC-C5: Get categories successfully

- Input:
    - valid userId
- Expected:
    - List of category names

---

### TC-C6: Get categories for invalid user

- Input:
    - invalid userId
- Expected:
    - ResourceNotFoundException

---

## 7. Summary

CategoryService is validated for:

- Correct creation rules
- Duplicate prevention
- User existence validation
- Retrieval correctness