# DU-Path Mapping Report

## ExpenseController → createExpense

---

# 1. Method Overview

This document provides a complete DU (Definition-Use) analysis for:

**Controller Method:**
`ExpenseController.createExpense(Long userId, CreateExpenseRequest request)`

The goal is to satisfy **All-DU-Paths Coverage**.

---

# 2. Key Variables

| Variable     | Type                 | Description         |
|--------------|----------------------|---------------------|
| userId       | Long                 | Input path variable |
| request      | CreateExpenseRequest | Input DTO           |
| amount       | BigDecimal           | Expense amount      |
| category     | Category             | Expense category    |
| participants | List<Long>           | Friend IDs          |
| user         | User                 | Loaded entity       |

---

# 3. DU Pairs Mapping

## 3.1 userId

- **Definition:** Controller parameter
- **Use:** `userService.findById(userId)`

DU Pair:

- D: method entry
- U: userService call

---

## 3.2 request.amount

- **Definition:** Request body
- **Use:** Expense creation logic

DU Pair:

- D: JSON deserialization
- U: expense creation in service

---

## 3.3 request.category

- **Definition:** Request body
- **Use:** Category validation + persistence

DU Pair:

- D: DTO mapping
- U: category assignment in Expense entity

---

## 3.4 participants

- **Definition:** Request body list
- **Use:** Split expense logic

DU Paths:

1. participants == null → skip splitting
2. participants != null → iterate and assign shares

---

## 3.5 user

- **Definition:** repository lookup
- **Use:** validation + ownership

DU Pair:

- D: `userRepository.findById`
- U: expense assignment

---

# 4. DU Paths (Definition-Clear Paths)

## Path 1: No Participants Flow

userId → findUser → createExpense → saveExpense

Coverage:

- userId DU covered
- request.amount DU covered
- category DU covered

---

## Path 2: With Participants Flow

userId → findUser → fetchFriends → splitExpense → saveExpense

Coverage:

- participants DU covered
- iteration DU covered
- service interaction DU covered

---

## Path 3: Invalid User Flow

userId → findUser → null → throw ResourceNotFoundException

Coverage:

- userId DU exception path

---

# 5. Test Case Mapping

| Test Case                         | Covered DU Paths |
|-----------------------------------|------------------|
| createExpense_withoutParticipants | Path 1           |
| createExpense_withParticipants    | Path 2           |
| createExpense_invalidUser         | Path 3           |

---

# 6. Coverage Conclusion

All DU pairs are covered at least once with:

- Definition-clear paths
- Valid + invalid flows
- Branch coverage of participant logic

✔ DU coverage: COMPLETE