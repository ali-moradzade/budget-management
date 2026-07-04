# ValidationService - DU Analysis

## Overview

This document presents DU (Definition-Use) analysis for ValidationService.
Each variable definition and its valid usage paths are identified according to All-DU-Paths coverage criteria.

---

## 1. getUserIfExists(Long userId)

### Variables

- userId (parameter)
- user (repository result)

### Definitions

- userId: method input
- user: result of `userRepository.findById(userId)`

### Uses

- userRepository.findById(userId) → uses userId
- return user → used by caller service

### DU Pair

| Variable | Definition        | Use             | Path              |
|----------|-------------------|-----------------|-------------------|
| userId   | method entry      | repository call | entry → findById  |
| user     | repository result | return          | findById → return |

---

## 2. getCategoryIfExists(Long userId, String categoryName)

### Variables

- userId
- categoryName
- category

### Definitions

- categoryName: parameter
- category: repository result

### Uses

- condition: categoryName != null
- repository query
- return category

### DU Pairs

| Variable     | Definition        | Use              | Path                  |
|--------------|-------------------|------------------|-----------------------|
| categoryName | parameter         | null check       | entry → condition     |
| categoryName | parameter         | repository query | entry → findCategory  |
| category     | repository result | return           | findCategory → return |

---

## 3. getExpenseIfExists(Long userId, Long expenseId)

### Variables

- userId
- expenseId
- expense

### DU Pairs

| Variable  | Definition        | Use               | Path                 |
|-----------|-------------------|-------------------|----------------------|
| userId    | parameter         | repository filter | entry → findExpense  |
| expenseId | parameter         | repository filter | entry → findExpense  |
| expense   | repository result | return            | findExpense → return |

---

## 4. getExpenseParticipantIfExists(Long userId, Long id)

### Variables

- userId
- id
- participant

### DU Pairs

| Variable    | Definition        | Use               | Path                     |
|-------------|-------------------|-------------------|--------------------------|
| id          | parameter         | findById          | entry → findById         |
| participant | repository result | second validation | findById → expense check |
| participant | validated result  | return            | validation → return      |

---

## 5. getFriendIfExists(Long userId, Long friendId)

### DU Pairs

| Variable | Definition        | Use              | Path                |
|----------|-------------------|------------------|---------------------|
| friendId | parameter         | repository query | entry → findFriend  |
| userId   | parameter         | repository query | entry → findFriend  |
| friend   | repository result | return           | findFriend → return |

---

## 6. getAllFriends(Long userId)

### DU Pairs

| Variable | Definition        | Use               | Path                  |
|----------|-------------------|-------------------|-----------------------|
| userId   | parameter         | repository query  | entry → findByUserId  |
| friends  | repository result | stream processing | findByUserId → return |

---

## 7. checkUserNameDuplication(String username)

### DU Pairs

| Variable | Definition | Use               | Path                       |
|----------|------------|-------------------|----------------------------|
| username | parameter  | repository query  | entry → findUserByUsername |
| username | parameter  | exception message | entry → exception          |
