# White-Box Analysis Report (DU + ICFG + Test Mapping)

Project: Budget Management System  
Module: Expense Management  
Author: White-box Testing Suite

---

# 1. Overview

This document contains the complete white-box testing analysis for the Expense module, including:

- Control Flow Models (ICFG references)
- Data Flow Analysis (DU definitions and DU-pairs)
- Test Coverage Mapping
- Traceability between DU-pairs and test cases

---

# 2. Structure Reference

ICFG diagrams are located at: `docs/white-box/icfg/`

DU analysis and mappings are located at: `docs/white-box/du/`

Black-box tests are located at: `docs/black-box/`


---

# 3. Core Module Under Analysis

## ExpenseController.createExpense

### Responsibilities:

- Create expense for a user
- Validate user existence
- Attach category
- Distribute shares among participants

---

## ExpenseService.createExpense

### Responsibilities:

- Create Expense entity
- Compute participant shares
- Persist Expense + ExpenseParticipants

---

## ExpenseParticipantService

### Responsibilities:

- Retrieve participant records
- Compute debts per friend
- Update payment status

---

## ValidationService

### Responsibilities:

- Central validation layer
- Entity existence checks
- Duplication checks
- Exception handling for invalid access

---

# 4. DU Analysis Summary

## Key Variables

- userId
- request
- user
- category
- friends
- expense
- participant
- shareAmount
- debts

---

## DU Characteristics

- Many variables follow **definition → computation use**
- ValidationService introduces **exception-based DU termination paths**
- ExpenseService contains **loop-based DU chains**
- ExpenseParticipantService contains **aggregation DU (reduce streams)**

---

# 5. DU-Pair Coverage Summary

| Module                    | DU Characteristics                    |
|---------------------------|---------------------------------------|
| ExpenseController         | request propagation, input validation |
| ExpenseService            | loop-based DU + computation chain     |
| ExpenseParticipantService | aggregation + update flows            |
| ValidationService         | exception-driven DU termination       |

---

# 6. Test Case Mapping (DU → Tests)

## TC1 — Create expense without participants

- Covers:
    - request null branch
    - single expense creation DU chain
    - no loop execution

---

## TC2 — Create expense with participants

- Covers:
    - loop-based DU chain
    - shareAmount computation
    - ExpenseParticipant creation
    - friend resolution DU path

---

## TC3 — Invalid user

- Covers:
    - exception-based DU path
    - missing user termination flow

---

## TC4 — Mark participant as paid

- Covers:
    - participant DEF → USE update
    - state transition DU path

---

## TC5 — Mark participant as unpaid

- Covers:
    - reverse state transition DU path

---

## TC6 — Get debts

- Covers:
    - aggregation DU path
    - stream reduction computation

---

# 7. DU Coverage Strategy

The following strategies were applied:

### 7.1 All-Defs Coverage

Each variable definition is exercised at least once.

### 7.2 All-Uses Coverage

Each definition reaches:

- at least one computational use (c-use)
- at least one predicate use (p-use)

### 7.3 Exception DU Coverage

ValidationService ensures:

- invalid states are also covered via exception paths

### 7.4 Loop DU Coverage

ExpenseService ensures:

- loop entry DU
- loop iteration DU
- loop exit DU

---

# 8. Conclusion

The system satisfies:

✔ Complete DU-pair coverage  
✔ Exception path coverage  
✔ Loop-based data flow coverage  
✔ Aggregation-based DU coverage  
✔ Full traceability between code and tests

This ensures strong white-box test adequacy for the Expense module.

---