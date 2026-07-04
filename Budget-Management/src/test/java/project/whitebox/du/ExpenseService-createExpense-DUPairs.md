# ExpenseService - createExpense DU-Pairs Analysis

## Method Under Test

`ExpenseService.createExpense(Long userId, CreateExpenseRequest request)`

---

## 1. Variables Analysis

We identify variables involved in DU analysis:

| Variable          | Type                 | Role                 |
|-------------------|----------------------|----------------------|
| userId            | Long                 | input parameter      |
| request           | CreateExpenseRequest | input parameter      |
| user              | User                 | definition/use       |
| category          | Category             | definition/use       |
| friends           | List<Friend>         | definition/use       |
| participantId     | Long                 | loop variable        |
| expense           | Expense              | definition/use       |
| participantsCount | int                  | computation variable |
| shareAmount       | Double               | computation variable |
| friend            | Friend               | loop variable        |
| participant       | ExpenseParticipant   | definition/use       |

---

## 2. Definitions (DEF points)

| Variable          | Definition Location                        |
|-------------------|--------------------------------------------|
| user              | validationService.getUserIfExists(userId)  |
| category          | validationService.getCategoryIfExists(...) |
| friends           | new ArrayList<>()                          |
| participantId     | for-loop iteration                         |
| expense           | new Expense(...)                           |
| participantsCount | friends.size() + 1                         |
| shareAmount       | request.amount() / participantsCount       |
| friend            | for-loop iteration                         |
| participant       | new ExpenseParticipant(...)                |

---

## 3. Uses (USE points)

| Variable          | Use Locations                                   |
|-------------------|-------------------------------------------------|
| user              | Expense constructor                             |
| category          | Expense constructor                             |
| friends           | condition check, size(), loop                   |
| participantId     | validationService.getFriendIfExists             |
| expense           | ExpenseParticipant constructor, repository save |
| participantsCount | share calculation                               |
| shareAmount       | ExpenseParticipant constructor                  |
| friend            | ExpenseParticipant constructor                  |
| participant       | repository save                                 |

---

## 4. DU Pairs

### 🔹 userId

- DU1: userId → validationService.getUserIfExists(userId)
    - Definition: parameter
    - Use: service call

---

### 🔹 request

- DU2: request → request.category()
- DU3: request → request.participants()
- DU4: request → request.amount()
- DU5: request → request.description()

---

### 🔹 user

- DU6: user = getUserIfExists → used in Expense constructor

---

### 🔹 category

- DU7: category = getCategoryIfExists → used in Expense constructor

---

### 🔹 friends

- DU8: friends = new ArrayList → used in if check
- DU9: friends → loop condition
- DU10: friends → size() computation

---

### 🔹 participantId

- DU11: participantId → getFriendIfExists(userId, participantId)

---

### 🔹 expense

- DU12: expense = new Expense → used in repository.save
- DU13: expense → used in ExpenseParticipant creation

---

### 🔹 participantsCount

- DU14: participantsCount = friends.size() + 1 → used in shareAmount

---

### 🔹 shareAmount

- DU15: shareAmount → used in ExpenseParticipant constructor

---

### 🔹 friend

- DU16: friend = loop variable → used in ExpenseParticipant creation

---

### 🔹 participant

- DU17: participant → saved via repository.save(participant)

---

## 5. Summary

This method has:

- 17 DU-pairs total
- 3 major execution flows:
    1. No participants
    2. With participants
    3. Category = null edge case

---
