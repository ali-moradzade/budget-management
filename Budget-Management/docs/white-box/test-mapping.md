# White-Box Test Mapping (DU Coverage)

## Overview

This document maps unit tests to DU (Definition-Use) pairs for each service in the Budget Management System.

The goal is to prove All-DU-Paths coverage using executed unit tests.

---

# 1. ValidationServiceTest

## Covered Methods

- getUserIfExists
- getCategoryIfExists
- getExpenseIfExists
- getFriendIfExists
- checkUserNameDuplication
- checkFriendNameDuplication
- checkCategoryNameDuplication

## DU Coverage Mapping

| Method                   | DU Variable  | Test Coverage            |
|--------------------------|--------------|--------------------------|
| getUserIfExists          | userId       | success + not found case |
| getCategoryIfExists      | categoryName | null + valid + not found |
| getExpenseIfExists       | expenseId    | valid + not found        |
| getFriendIfExists        | friendId     | valid + not found        |
| checkUserNameDuplication | username     | exists + not exists      |

---

# 2. UserServiceTest

## Covered Methods

- createUser

## DU Coverage Mapping

| Variable | DU Role         | Test Coverage                   |
|----------|-----------------|---------------------------------|
| username | definition/use  | duplicate + valid user creation |
| user     | entity creation | save + return ID                |

---

# 3. FriendServiceTest

## Covered Methods

- addFriend
- getUserFriends

## DU Coverage Mapping

| Variable   | DU Role                | Test Coverage        |
|------------|------------------------|----------------------|
| userId     | validation             | valid + invalid user |
| friendName | duplication + creation | duplicate + valid    |
| friend     | entity                 | save + mapping       |
| friends    | repository result      | list mapping         |

---

# 4. CategoryServiceTest

## Covered Methods

- createCategory
- getUserCategories

## DU Coverage Mapping

| Variable     | DU Role               | Test Coverage     |
|--------------|-----------------------|-------------------|
| categoryName | validation + creation | duplicate + valid |
| userId       | validation            | valid + invalid   |
| category     | entity                | save + mapping    |

---

# 5. ExpenseServiceTest

## Covered Methods

- createExpense
- getUserExpenses
- getExpense

## DU Coverage Mapping

| Variable    | DU Role             | Test Coverage                  |
|-------------|---------------------|--------------------------------|
| userId      | validation          | valid + invalid                |
| request     | input extraction    | participants + null cases      |
| category    | optional definition | null + valid                   |
| friends     | loop input          | empty + multiple               |
| shareAmount | computation         | single + multiple participants |
| expense     | entity creation     | save + retrieval               |
| expenseId   | validation          | found + not found              |

---

# 6. ExpenseParticipantServiceTest

## Covered Methods

- getExpenseParticipants
- getAllDebts
- getDebt
- markAsPaid
- markAsUnpaid

## DU Coverage Mapping

| Variable     | DU Role           | Test Coverage      |
|--------------|-------------------|--------------------|
| userId       | validation        | valid + invalid    |
| expenseId    | validation        | valid + invalid    |
| friendId     | mapping           | debt calculation   |
| participants | repository result | list mapping       |
| debts        | aggregation       | sum correctness    |
| participant  | state mutation    | paid/unpaid toggle |

---

# Final Notes

## Coverage Summary

All services achieve:

- ✔ All-DU-Paths coverage (logical approximation in unit tests)
- ✔ Branch coverage via success/failure tests
- ✔ Data-flow validation across service layer

## Important Constraint

Mockito-based unit tests are used for repository isolation, ensuring:

- deterministic behavior
- no database dependency
- fast execution

---

# END OF WHITE-BOX TEST MAPPINGF