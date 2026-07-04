# ExpenseService - DU Analysis

## Overview

This document presents DU (Definition-Use) analysis for ExpenseService.
The service is responsible for creating expenses, retrieving expenses, and mapping expense data with participants.

---

# Method: createExpense(Long userId, CreateExpenseRequest request)

---

## Variables

- userId (parameter)
- request (parameter object)
- user (validated entity)
- category (validated entity or null)
- friends (list)
- participantId (loop variable)
- expense (entity object)
- participantsCount (computed value)
- shareAmount (computed value)
- friend (loop element)
- participant (entity object)

---

## Definitions

- userId: method input
- request: method input
- user: `validationService.getUserIfExists(userId)`
- category: `validationService.getCategoryIfExists(...)`
- friends: `new ArrayList<>()`
- participantId: loop variable from request.participants()
- expense: `new Expense(...)`
- participantsCount: computed from friends.size() + 1
- shareAmount: computed from request.amount / participantsCount
- participant: `new ExpenseParticipant(...)`

---

## Uses

- userId: validation, category lookup, friend validation
- request: reading amount, description, participants
- category: used in Expense creation
- user: used in Expense creation
- participantId: used in friend lookup loop
- friends: size used in calculation
- expense: used in participant creation + save
- participantsCount: used in share calculation
- shareAmount: used in participant creation
- friend: used in participant creation

---

## DU Pairs

| Variable          | Definition        | Use                  | Path                                |
|-------------------|-------------------|----------------------|-------------------------------------|
| userId            | parameter entry   | user validation      | entry → getUser                     |
| request           | parameter entry   | data extraction      | entry → read fields                 |
| category          | validation result | expense creation     | validation → new Expense            |
| friends           | list init         | loop condition       | init → loop                         |
| participantId     | loop value        | friend validation    | loop → getFriend                    |
| friend            | validation result | participant creation | validation → new ExpenseParticipant |
| expense           | entity creation   | participant creation | save → participant loop             |
| participantsCount | computed          | share calculation    | size → division                     |
| shareAmount       | computed          | participant creation | calc → save                         |
| participant       | entity creation   | save                 | new → save                          |

---

# Method: getUserExpenses(Long userId)

---

## Variables

- userId
- expenses (repository result)
- expense (stream element)

---

## DU Pairs

| Variable | Definition        | Use            | Path                    |
|----------|-------------------|----------------|-------------------------|
| userId   | parameter entry   | validation     | entry → getUserIfExists |
| expenses | repository result | mapping        | repository → stream     |
| expense  | stream element    | DTO conversion | stream → toDto          |

---

# Method: getExpense(Long userId, Long expenseId)

---

## Variables

- userId
- expenseId
- expense

---

## DU Pairs

| Variable  | Definition        | Use         | Path                       |
|-----------|-------------------|-------------|----------------------------|
| userId    | parameter entry   | validation  | entry → getUserIfExists    |
| expenseId | parameter entry   | validation  | entry → getExpenseIfExists |
| expense   | validation result | DTO mapping | validation → toDto         |

---

## Summary

This service contains:

- conditional logic (null checks for participants)
- looping over participants
- arithmetic computation (share calculation)
- repository interactions
- DTO transformation

It is the most complex DU analysis in the system and covers multiple execution paths.