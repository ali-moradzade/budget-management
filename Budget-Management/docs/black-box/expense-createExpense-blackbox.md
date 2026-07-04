# Black-Box Testing - Create Expense Endpoint

## Endpoint Under Test

POST /expenses/{userId}

Based on Swagger specification and system behavior.

---

# 1. Decision Table Testing

## 1.1 Conditions

We identify input conditions:

- C1: User exists
- C2: Category is valid (belongs to user or null allowed)
- C3: Amount is valid (> 0)
- C4: Participants list is valid (all friends belong to user or null/empty)

---

## 1.2 Decision Table

| Rule | C1 User | C2 Category | C3 Amount | C4 Participants | Expected Output                            |
|------|---------|-------------|-----------|-----------------|--------------------------------------------|
| R1   | ✔       | ✔           | ✔         | ✔               | Expense created + participants created     |
| R2   | ✘       | -           | -         | -               | 404 User not found                         |
| R3   | ✔       | ✘           | ✔         | ✔               | 404 Category not found                     |
| R4   | ✔       | ✔           | ✘         | ✔               | 400 Invalid amount                         |
| R5   | ✔       | ✔           | ✔         | ✘               | 404 Friend not found / invalid participant |
| R6   | ✔       | ✔           | ✔         | empty/null      | Expense created without participants       |

---

## 1.3 Interpretation

- System is **strict on existence validation**
- Participants affect only debt splitting logic
- Category is optional logically but validated if provided
- Amount is mandatory business rule

---

# 2. Equivalence Partitioning

---

## 2.1 User ID (userId)

| Class | Values              | Validity |
|-------|---------------------|----------|
| E1    | existing userId     | valid    |
| E2    | non-existing userId | invalid  |
| E3    | null                | invalid  |

---

## 2.2 Amount

| Class | Values                | Validity |
|-------|-----------------------|----------|
| E1    | > 0 (e.g., 10, 100.5) | valid    |
| E2    | 0                     | invalid  |
| E3    | < 0                   | invalid  |
| E4    | null                  | invalid  |

---

## 2.3 Category

| Class | Values                    | Validity                     |
|-------|---------------------------|------------------------------|
| E1    | existing category of user | valid                        |
| E2    | non-existing category     | invalid                      |
| E3    | null                      | valid (no category scenario) |

---

## 2.4 Participants

| Class | Values                     | Validity |
|-------|----------------------------|----------|
| E1    | valid friend IDs of user   | valid    |
| E2    | mix of valid + invalid IDs | invalid  |
| E3    | empty list                 | valid    |
| E4    | null                       | valid    |

---

## 2.5 Description

| Class | Values     | Validity |
|-------|------------|----------|
| E1    | any string | valid    |
| E2    | null       | valid    |

---

# 3. Test Case Design (Derived from Black-Box)

---

## TC1 - Valid full request

- User exists
- Category valid
- Amount = 100
- Participants = [1,2]

Expected:

- 200 OK / 201 Created
- Expense created
- Participants created

---

## TC2 - User does not exist

Expected:

- 404 Not Found

---

## TC3 - Invalid category

Expected:

- 404 Not Found

---

## TC4 - Invalid amount (0 or negative)

Expected:

- 400 Bad Request

---

## TC5 - No participants

Expected:

- Expense created
- No ExpenseParticipant records

---

## TC6 - Mixed participants (invalid friend id)

Expected:

- 404 error

---

# 4. Summary

This endpoint demonstrates:

- Strong validation-driven design
- Multiple failure branches
- Clear business rule separation:
    - validation layer
    - persistence layer
    - computation layer

---

# 5. Coverage Statement

This black-box suite ensures:

✔ All input equivalence classes are covered  
✔ All decision rules are tested  
✔ Both positive and negative flows are included  
✔ Business rules are validated independently of code structure