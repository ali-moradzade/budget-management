# ExpenseService - DU Pair to Test Mapping

## 1. Purpose

This document maps existing unit tests to DU pairs identified in:

- ExpenseService-createExpense-DUPairs.md

No new tests are created here — only coverage mapping.

---

## 2. Existing Unit Tests

| Test Name                                               | Purpose                   |
|---------------------------------------------------------|---------------------------|
| createExpense_withoutParticipants_shouldSaveOnlyExpense | No participant flow       |
| createExpense_withParticipants_shouldCreateParticipants | Participant creation flow |
| createExpense_shouldSplitAmountCorrectly                | Computation correctness   |

---

## 3. DU Pair Coverage Mapping

---

### 🔹 userId (DU1)

- Definition: method parameter
- Use: validationService.getUserIfExists(userId)

| Covered by |
|------------|
| All tests  |

---

### 🔹 request (DU2–DU5)

- Uses: category, amount, participants, description

| Covered by                                     |
|------------------------------------------------|
| All tests (implicitly via createExpense calls) |

---

### 🔹 user (DU6)

- Def: validationService.getUserIfExists
- Use: Expense constructor

| Covered by |
|------------|
| All tests  |

---

### 🔹 category (DU7)

- Def: validationService.getCategoryIfExists
- Use: Expense constructor

| Covered by |
|------------|
| All tests  |

---

### 🔹 friends (DU8–DU10)

- Def: new ArrayList + conditional + size()

| Covered by                            |
|---------------------------------------|
| withoutParticipants test → empty path |
| withParticipants test → loop path     |
| splitAmount test → size() usage       |

---

### 🔹 participantId (DU11)

- Use: validationService.getFriendIfExists

| Covered by            |
|-----------------------|
| withParticipants test |
| splitAmount test      |

---

### 🔹 expense (DU12–DU13)

- Def: new Expense
- Use: repository.save + participant creation

| Covered by                               |
|------------------------------------------|
| All tests (save verification indirectly) |

---

### 🔹 participantsCount (DU14)

- Def: friends.size() + 1
- Use: share calculation

| Covered by                |
|---------------------------|
| splitAmountCorrectly test |

---

### 🔹 shareAmount (DU15)

- Def: request.amount() / participantsCount
- Use: ExpenseParticipant constructor

| Covered by                |
|---------------------------|
| splitAmountCorrectly test |

---

### 🔹 friend (DU16)

- Loop variable usage

| Covered by            |
|-----------------------|
| withParticipants test |
| splitAmount test      |

---

### 🔹 participant (DU17)

- Saved via repository.save

| Covered by            |
|-----------------------|
| withParticipants test |

---

## 4. Coverage Summary

| DU Category         | Status     |
|---------------------|------------|
| Definitions covered | ✔ Complete |
| Uses covered        | ✔ Complete |
| Loop paths          | ✔ Covered  |
| Branch paths        | ✔ Covered  |

---

## 5. Conclusion

Existing unit tests already satisfy:

- All-DU-Paths coverage requirement
- Branch coverage for createExpense()
- Loop + conditional execution paths

No additional unit tests are required for ExpenseService.