# Friend Service - Black Box Testing

## 1. Overview

This document defines black-box test cases for FriendService.
Testing is based on:

- Decision Table Testing
- Equivalence Partitioning

Focus is on external behavior via API/service calls, not implementation.

---

## 2. Functionality Under Test

FriendService provides:

1. Add Friend
2. Get User Friends

---

## 3. Decision Table Testing

### 3.1 Add Friend (createFriend)

| User Valid | Friend Name Duplicate | Friend Name Valid | Expected Result        |
|------------|-----------------------|-------------------|------------------------|
| ✔          | ✘                     | ✔                 | Friend created         |
| ✘          | -                     | ✔                 | User not found error   |
| ✔          | ✔                     | ✔                 | Duplicate friend error |
| ✔          | ✘                     | ✘ (null/empty)    | Validation error       |

---

## 4. Equivalence Partitioning

### 4.1 User ID

- Valid user → exists in DB
- Invalid user → does not exist → ResourceNotFoundException

---

### 4.2 Friend Name

- Valid: non-empty string (e.g. "Ali")
- Invalid: null, empty string
- Duplicate: already exists for same user

---

### 4.3 Friend Phone

- Valid: non-null string (e.g. "+989121234567")
- Invalid: null or malformed format

---

## 5. Test Cases

### TC-F1: Successfully add friend

- Input:
    - userId: valid
    - friendName: "Ali"
    - friendPhone: "+989121234567"
- Expected:
    - Friend saved
    - No exception

---

### TC-F2: Add friend with invalid user

- Input:
    - userId: invalid
- Expected:
    - ResourceNotFoundException

---

### TC-F3: Duplicate friend name

- Input:
    - userId: valid
    - friendName: existing name
- Expected:
    - ResourceAlreadyExistsException

---

### TC-F4: Invalid friend name

- Input:
    - friendName: null or ""
- Expected:
    - Validation error (400)

---

## 6. Get User Friends

### TC-F5: Get friends successfully

- Input:
    - valid userId
- Expected:
    - List of FriendResponse

---

### TC-F6: Get friends for invalid user

- Input:
    - invalid userId
- Expected:
    - ResourceNotFoundException

---

## 7. Summary

FriendService is validated for:

- Correct friend creation rules
- Duplicate prevention
- User existence validation
- Retrieval correctness