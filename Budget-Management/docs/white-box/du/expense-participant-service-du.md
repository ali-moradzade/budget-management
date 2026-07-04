# ExpenseParticipantService - DU Analysis

## Overview

This document presents DU (Definition-Use) analysis for ExpenseParticipantService.
The service handles expense participants, debts, and payment status updates.

---

# Method: getExpenseParticipants(Long userId, Long expenseId)

---

## Variables

- userId (parameter)
- expenseId (parameter)
- participants (repository result)
- participant (stream element)

---

## Definitions

- userId: method input
- expenseId: method input
- participants: `expenseParticipantRepository.findByExpenseId(expenseId)`
- participant: stream element from participants

---

## Uses

- userId: validation
- expenseId: validation + repository query
- participants: stream processing
- participant: DTO conversion

---

## DU Pairs

| Variable     | Definition        | Use                | Path                       |
|--------------|-------------------|--------------------|----------------------------|
| userId       | parameter entry   | validation         | entry → getUserIfExists    |
| expenseId    | parameter entry   | validation + query | entry → getExpenseIfExists |
| participants | repository result | mapping            | repository → stream        |
| participant  | stream element    | DTO conversion     | stream → toDto             |

---

# Method: getAllDebts(Long userId)

---

## Variables

- userId
- friends (repository result)
- friendId (mapped variable)
- debt (computed result)

---

## Definitions

- userId: method input
- friends: `validationService.getAllFriends(userId)`
- friendId: stream mapping of Friend IDs
- debt: result of `getDebt(userId, friendId)`

---

## DU Pairs

| Variable | Definition        | Use              | Path                    |
|----------|-------------------|------------------|-------------------------|
| userId   | parameter entry   | validation       | entry → getUserIfExists |
| friends  | repository result | stream mapping   | repository → stream     |
| friendId | mapped value      | debt computation | stream → getDebt        |
| debt     | computed result   | final list       | getDebt → return        |

---

# Method: getParticipation(Long userId, Long friendId)

---

## Variables

- userId
- friendId
- participations

---

## DU Pairs

| Variable       | Definition        | Use            | Path                      |
|----------------|-------------------|----------------|---------------------------|
| userId         | parameter entry   | validation     | entry → getUserIfExists   |
| friendId       | parameter entry   | validation     | entry → getFriendIfExists |
| participations | repository result | stream mapping | repository → stream       |

---

# Method: getDebt(Long userId, Long friendId)

---

## Variables

- userId
- friend
- debts (list of amounts)
- totalDebt

---

## Definitions

- userId: method input
- friend: validated entity
- debts: list of unpaid shares
- totalDebt: aggregation result

---

## Uses

- userId: validation
- friendId: friend lookup
- friend: used for name in response
- debts: used for summation
- totalDebt: returned in response DTO

---

## DU Pairs

| Variable  | Definition        | Use               | Path              |
|-----------|-------------------|-------------------|-------------------|
| userId    | parameter entry   | validation        | entry → getUser   |
| friendId  | parameter entry   | friend lookup     | entry → getFriend |
| friend    | validation result | response creation | validation → DTO  |
| debts     | repository + map  | aggregation       | query → sum       |
| totalDebt | computed sum      | return            | sum → return      |

---

# Method: markAsPaid(Long userId, Long id)

---

## Variables

- userId
- id
- participant

---

## DU Pairs

| Variable    | Definition        | Use                | Path                          |
|-------------|-------------------|--------------------|-------------------------------|
| userId      | parameter entry   | validation         | entry → getUser               |
| id          | parameter entry   | participant lookup | entry → getExpenseParticipant |
| participant | validation result | state change       | validation → setPaid          |
| participant | modified object   | save               | update → save                 |

---

# Method: markAsUnpaid(Long userId, Long id)

Same DU structure as markAsPaid, with different state transition.

---

## Summary

This service includes:

- aggregation (sum of debts)
- mapping transformations (Friend → ID)
- stream processing pipelines
- state mutation (paid/unpaid flags)

It is one of the most important services for demonstrating data-flow coverage.