# White-Box Testing Final Report

## 1. Scope

This document summarizes the white-box testing analysis of the Budget Management system.  
It includes:

- Control Flow analysis (ICFG summary)
- DU-pair coverage strategy
- Unit test mapping
- Coverage validation per service

---

## 2. Analyzed Components

- UserService
- FriendService
- CategoryService
- ExpenseService
- ExpenseParticipantService
- ValidationService

---

## 3. Methodology

We used the following white-box techniques:

### 3.1 DU-Pair Analysis

- Identified definition-use pairs for variables
- Ensured all critical paths are testable
- Mapped DU pairs to unit test cases

### 3.2 Control Flow Analysis (ICFG abstraction)

- Modeled method-level control flow
- Focused on:
    - conditional branches
    - loops
    - exception paths

---

## 4. Service-Level Summary

### 4.1 UserService

- Key DU: username → validation → persistence
- Paths:
    - success path (unique username)
    - exception path (duplicate username)

Test coverage:

- createUser_success
- createUser_duplicateUsername_exception

---

### 4.2 FriendService

- Key DU: userId → validation → friend creation
- Paths:
    - valid user + new friend
    - duplicate friend name exception

Test coverage:

- addFriend_success
- addFriend_duplicateFriend_exception
- getUserFriends_success

---

### 4.3 CategoryService

- Key DU: userId + categoryName
- Paths:
    - create category success
    - duplicate category exception
    - fetch categories list

Test coverage:

- createCategory_success
- createCategory_duplicate_exception
- getUserCategories_success

---

### 4.4 ExpenseService

- Key DU:
    - userId → validation
    - request.amount → share calculation
    - participants list → iteration + persistence

Paths:

- expense without participants
- expense with participants
- participant share distribution

Test coverage:

- createExpense_withoutParticipants
- createExpense_withParticipants
- getUserExpenses
- getExpense

---

### 4.5 ExpenseParticipantService

- Key DU:
    - expenseId → participant retrieval
    - friendId → debt computation
    - paid flag → filtering logic

Paths:

- fetch participants by expense
- fetch participation by friend
- compute debts
- mark paid/unpaid transitions

Test coverage:

- getExpenseParticipants_success
- getParticipation_success
- getAllDebts_success
- markAsPaid/unpaid

---

### 4.6 ValidationService

- Central dependency validation layer
- Handles:
    - existence checks
    - duplication checks
    - cross-entity validation

Paths:

- entity exists → return
- entity missing → exception
- duplication detected → exception

Test coverage:

- getUserIfExists
- getFriendIfExists
- getExpenseIfExists
- duplication checks

---

## 5. DU-Pair Coverage Summary

| Service                   | DU Coverage | Status |
|---------------------------|-------------|--------|
| UserService               | Complete    | ✔      |
| FriendService             | Complete    | ✔      |
| CategoryService           | Complete    | ✔      |
| ExpenseService            | Complete    | ✔      |
| ExpenseParticipantService | Complete    | ✔      |
| ValidationService         | Complete    | ✔      |

---

## 6. Limitations

- ICFGs are abstracted (method-level only)
- No concurrency testing included
- No database integration in white-box scope

---

## 7. Conclusion

All services achieve:

- Full DU-pair coverage for critical variables
- Branch-level coverage via unit tests
- Exception path validation
- Business logic verification

White-box testing phase is complete.