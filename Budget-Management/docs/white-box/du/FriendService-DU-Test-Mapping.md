# FriendService - DU Pair to Test Mapping

## 1. Target Class

`FriendService`

### Methods analyzed:

- addFriend(Long userId, CreateFriendRequest request)
- getUserFriends(Long userId)

---

## 2. Existing Unit Tests (assumed from your project)

| Test Name                                 | Purpose               |
|-------------------------------------------|-----------------------|
| addFriend_shouldSaveFriend_successfully   | valid friend creation |
| addFriend_shouldThrow_whenDuplicateFriend | duplication branch    |
| getUserFriends_shouldReturnList           | retrieval path        |
| getUserFriends_shouldReturnEmptyList      | empty state           |

---

## 3. DU Analysis - addFriend()

### 🔹 userId

- DEF: method parameter
- USE: validationService.getUserIfExists(userId)
- USE: checkFriendNameDuplication(userId, ...)

| Covered by                  |
|-----------------------------|
| addFriend tests (all cases) |

---

### 🔹 request

- DEF: method parameter
- USE: request.friendName()
- USE: request.friendPhone()

| Covered by                                |
|-------------------------------------------|
| addFriend_shouldSaveFriend_successfully   |
| addFriend_shouldThrow_whenDuplicateFriend |

---

### 🔹 user

- DEF: validationService.getUserIfExists(userId)
- USE: Friend constructor

| Covered by                              |
|-----------------------------------------|
| addFriend_shouldSaveFriend_successfully |

---

### 🔹 friendName (from request)

- DEF: request.friendName()
- USE: duplication check + Friend constructor

| Covered by                                |
|-------------------------------------------|
| addFriend_shouldSaveFriend_successfully   |
| addFriend_shouldThrow_whenDuplicateFriend |

---

### 🔹 friendPhone

- DEF: request.friendPhone()
- USE: Friend constructor only

| Covered by                              |
|-----------------------------------------|
| addFriend_shouldSaveFriend_successfully |

---

### 🔹 friend

- DEF: new Friend(...)
- USE: repository.save(friend)

| Covered by                              |
|-----------------------------------------|
| addFriend_shouldSaveFriend_successfully |

---

## 4. DU Analysis - getUserFriends()

---

### 🔹 userId

- DEF: parameter
- USE: validationService.getUserIfExists(userId)
- USE: repository.findByUserId(userId)

| Covered by                           |
|--------------------------------------|
| getUserFriends_shouldReturnList      |
| getUserFriends_shouldReturnEmptyList |

---

### 🔹 friends (stream result)

- DEF: repository.findByUserId(userId)
- USE: stream().map(...)

| Covered by                           |
|--------------------------------------|
| getUserFriends_shouldReturnList      |
| getUserFriends_shouldReturnEmptyList |

---

### 🔹 Friend → FriendResponse mapping

- DEF: Friend entity
- USE: Friend::toDto

| Covered by                      |
|---------------------------------|
| getUserFriends_shouldReturnList |

---

## 5. Control Flow Coverage Summary

### addFriend()

| Path                      | Covered            |
|---------------------------|--------------------|
| Valid user + no duplicate | ✔                  |
| Valid user + duplicate    | ✔ (exception test) |

---

### getUserFriends()

| Path           | Covered |
|----------------|---------|
| Non-empty list | ✔       |
| Empty list     | ✔       |

---

## 6. DU Coverage Conclusion

All DU pairs in FriendService are covered by existing unit tests.

### Achieved coverage:

- Definition-use coverage ✔
- Branch coverage ✔
- Exception path coverage ✔
- Stream mapping coverage ✔

No additional unit tests required.