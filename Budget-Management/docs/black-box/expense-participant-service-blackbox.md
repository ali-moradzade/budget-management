# ExpenseParticipant Service - Black Box Testing

## 1. Overview

This document defines black-box test cases for ExpenseParticipantService.

Testing techniques:

- Decision Table Testing
- Equivalence Partitioning

Focus is on external behavior via service/API calls.

---

## 2. Functionality Under Test

ExpenseParticipantService provides:

1. Get Expense Participants
2. Get User Debts
3. Get Friend Participation
4. Get Single Debt
5. Mark as Paid / Unpaid

---

## 3. Decision Table Testing

### 3.1 Get Expense Participants

| User Valid | Expense Valid | Expected Result         |
|------------|---------------|-------------------------|
| ✔          | ✔             | List of participants    |
| ✘          | ✔             | User not found error    |
| ✔          | ✘             | Expense not found error |

---

### 3.2 Get Debt

| User Valid | Friend Valid | Has Unpaid Debts | Expected Result        |
|------------|--------------|------------------|------------------------|
| ✔          | ✔            | ✔                | DebtResponse with sum  |
| ✔          | ✔            | ✘                | Debt = 0               |
| ✘          | ✔            | -                | User not found error   |
| ✔          | ✘            | -                | Friend not found error |

---

### 3.3 Mark as Paid/Unpaid

| User Valid | Participant Exists | Expected Result             |
|------------|--------------------|-----------------------------|
| ✔          | ✔                  | Status updated              |
| ✘          | ✔                  | User not found error        |
| ✔          | ✘                  | Participant not found error |

---

## 4. Equivalence Partitioning

### 4.1 User ID

- Valid: user exists
- Invalid: user does not exist → ResourceNotFoundException

---

### 4.2 Expense ID

- Valid: belongs to user
- Invalid: does not belong to user → ResourceNotFoundException

---

### 4.3 Friend ID

- Valid: exists for user
- Invalid: not found → ResourceNotFoundException

---

### 4.4 Expense Participant

- Valid: exists and belongs to expense
- Invalid: missing or unrelated → ResourceNotFoundException

---

### 4.5 Paid Status

- Valid: true / false toggle
- Invalid: N/A (boolean only)

---

## 5. Test Cases

### TC-P1: Get participants of expense

- Input:
    - valid userId
    - valid expenseId
- Expected:
    - List of ExpenseParticipantResponse

---

### TC-P2: Get participants with invalid expense

- Input:
    - invalid expenseId
- Expected:
    - ResourceNotFoundException

---

### TC-P3: Get all debts

- Input:
    - valid userId
- Expected:
    - List of DebtResponse per friend

---

### TC-P4: Get debt for friend with unpaid expenses

- Input:
    - valid userId
    - friendId
- Expected:
    - Sum of unpaid shares

---

### TC-P5: Mark participant as paid

- Input:
    - valid userId
    - valid participantId
- Expected:
    - participant.paid = true

---

### TC-P6: Mark participant as unpaid

- Input:
    - valid userId
    - valid participantId
- Expected:
    - participant.paid = false

---

## 6. Summary

ExpenseParticipantService is validated for:

- Debt calculation correctness
- Payment state transitions
- Access control via validation service
- Proper exception handling