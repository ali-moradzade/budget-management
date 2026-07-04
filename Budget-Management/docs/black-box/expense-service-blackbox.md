# Expense Service - Black Box Testing

## 1. Overview

This document defines black-box test cases for ExpenseService.

Testing techniques:

- Decision Table Testing
- Equivalence Partitioning

Focus is on system behavior via API/service calls, not internal logic.

---

## 2. Functionality Under Test

ExpenseService provides:

1. Create Expense
2. Get User Expenses
3. Get Single Expense

---

## 3. Decision Table Testing

### 3.1 Create Expense

| User Valid | Category Valid | Participants Valid | Amount Valid      | Expected Result                        |
|------------|----------------|--------------------|-------------------|----------------------------------------|
| ✔          | ✔              | ✔                  | ✔                 | Expense created + participants created |
| ✘          | ✔              | ✔                  | ✔                 | User not found error                   |
| ✔          | ✘              | ✔                  | ✔                 | Category not found error               |
| ✔          | ✔              | ✘ (invalid friend) | ✔                 | Friend not found error                 |
| ✔          | ✔              | ✔                  | ✘ (null/negative) | Validation error                       |

---

## 4. Equivalence Partitioning

### 4.1 User ID

- Valid: user exists in DB
- Invalid: user does not exist → ResourceNotFoundException

---

### 4.2 Category

- Valid: category exists for user
- Invalid: category does not exist → ResourceNotFoundException
- Null category: allowed (service returns null category → depends on design)

---

### 4.3 Participants List

- Valid:
    - empty list → expense created without participants
    - list of valid friend IDs

- Invalid:
    - friend ID does not exist → ResourceNotFoundException

---

### 4.4 Expense Amount

- Valid:
    - positive double value (e.g. 100.0)

- Invalid:
    - null
    - zero
    - negative value

---

### 4.5 Description

- Valid:
    - any non-null string

- Invalid:
    - null (depending on DB constraint)

---

## 5. Test Cases

### TC-E1: Create expense without participants

- Input:
    - valid user
    - valid category
    - amount = 100
    - participants = null / empty
- Expected:
    - Expense created
    - No ExpenseParticipant created

---

### TC-E2: Create expense with participants

- Input:
    - valid user
    - valid category
    - participants = [friend1, friend2]
- Expected:
    - Expense created
    - ExpenseParticipant created for each friend
    - Share amount calculated correctly

---

### TC-E3: Invalid user

- Input:
    - invalid userId
- Expected:
    - ResourceNotFoundException

---

### TC-E4: Invalid category

- Input:
    - category not belonging to user
- Expected:
    - ResourceNotFoundException

---

### TC-E5: Invalid participant

- Input:
    - participants contain invalid friendId
- Expected:
    - ResourceNotFoundException

---

### TC-E6: Get user expenses

- Input:
    - valid userId
- Expected:
    - List of ExpenseResponse

---

### TC-E7: Get expense by ID

- Input:
    - valid userId
    - valid expenseId
- Expected:
    - ExpenseResponse returned

---

## 6. Summary

ExpenseService is validated for:

- Core expense creation logic
- Participant splitting logic
- Validation delegation correctness
- Retrieval operations
- Exception handling for invalid inputs