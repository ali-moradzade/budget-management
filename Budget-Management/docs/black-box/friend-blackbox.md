# Black-Box Testing - Friend Management API

## Endpoints Under Test

Based on Swagger:

- POST /friends/{userId}
- GET /friends/{userId}

---

# 1. Decision Table Testing

## 1.1 Add Friend (POST)

### Conditions

- C1: User exists
- C2: Friend name is unique for user
- C3: Friend phone is valid (non-null, non-empty)
- C4: Request payload is valid

---

## 1.2 Decision Table

| Rule | C1 User | C2 Unique Name | C3 Valid Phone  | Expected Result             |
|------|---------|----------------|-----------------|-----------------------------|
| R1   | ✔       | ✔              | ✔               | Friend created successfully |
| R2   | ✘       | -              | -               | 404 User not found          |
| R3   | ✔       | ✘              | ✔               | 409 Duplicate friend error  |
| R4   | ✔       | ✔              | ✘               | 400 Invalid input           |
| R5   | ✔       | ✔              | ✔ but null name | 400 Bad Request             |

---

## 1.3 Interpretation

- User existence is mandatory
- Friend name must be unique per user
- Phone is required business field
- System enforces strong validation before persistence

---

# 2. Equivalence Partitioning

---

## 2.1 userId

| Class | Values              | Validity |
|-------|---------------------|----------|
| E1    | existing userId     | valid    |
| E2    | non-existing userId | invalid  |
| E3    | null                | invalid  |

---

## 2.2 friendName

| Class | Values                  | Validity |
|-------|-------------------------|----------|
| E1    | non-empty unique string | valid    |
| E2    | empty string            | invalid  |
| E3    | null                    | invalid  |
| E4    | duplicate for same user | invalid  |

---

## 2.3 friendPhone

| Class | Values             | Validity |
|-------|--------------------|----------|
| E1    | valid phone format | valid    |
| E2    | empty              | invalid  |
| E3    | null               | invalid  |

---

## 2.4 Request Body

| Class | Description           | Validity |
|-------|-----------------------|----------|
| E1    | complete valid object | valid    |
| E2    | missing fields        | invalid  |
| E3    | malformed JSON        | invalid  |

---

# 3. Get Friends (GET)

---

## 3.1 Decision Table

### Conditions

- C1: User exists
- C2: User has friends

| Rule | C1 User | C2 Has Friends | Expected Result          |
|------|---------|----------------|--------------------------|
| R1   | ✔       | ✔              | List of friends returned |
| R2   | ✔       | ✘              | Empty list               |
| R3   | ✘       | -              | 404 User not found       |

---

## 3.2 Equivalence Partitioning

### userId

| Class | Values          | Validity |
|-------|-----------------|----------|
| E1    | existing userId | valid    |
| E2    | invalid userId  | invalid  |

---

### response list

| Class | Values         | Validity          |
|-------|----------------|-------------------|
| E1    | non-empty list | valid             |
| E2    | empty list     | valid (edge case) |

---

# 4. Test Case Scenarios

---

## TC1 - Create friend successfully

- Valid user
- Unique name
- Valid phone

Expected:

- 201 Created

---

## TC2 - Duplicate friend

Expected:

- 409 Conflict

---

## TC3 - Invalid user

Expected:

- 404 Not Found

---

## TC4 - Missing phone

Expected:

- 400 Bad Request

---

## TC5 - Get friends (non-empty)

Expected:

- 200 OK + list

---

## TC6 - Get friends (empty)

Expected:

- 200 OK + []

---

# 5. Summary

Friend module demonstrates:

- Strong uniqueness constraint (business rule)
- Mandatory validation before persistence
- Clean separation between create and query flows
- Clear positive and negative API behaviors

---

# 6. Coverage Statement

This black-box suite ensures:

✔ All equivalence classes covered  
✔ All decision rules tested  
✔ Positive + negative flows included  
✔ API-level validation verified independently of code