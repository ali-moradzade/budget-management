# CategoryService - DU Pair to Test Mapping

## 1. Target Class

`CategoryService`

### Methods analyzed:

- createCategory(Long userId, String categoryName)
- getUserCategories(Long userId)

---

## 2. Existing Unit Tests (assumed from your suite)

| Test Name                                        | Purpose              |
|--------------------------------------------------|----------------------|
| createCategory_shouldSaveCategory_successfully   | normal creation flow |
| createCategory_shouldThrow_whenDuplicateCategory | duplication branch   |
| getUserCategories_shouldReturnList               | normal retrieval     |
| getUserCategories_shouldReturnEmptyList          | empty state          |

---

## 3. DU Analysis - createCategory()

---

### 🔹 userId

- DEF: method parameter
- USE: validationService.getUserIfExists(userId)
- USE: categoryRepository.save(category)

| Covered by                                       |
|--------------------------------------------------|
| createCategory_shouldSaveCategory_successfully   |
| createCategory_shouldThrow_whenDuplicateCategory |

---

### 🔹 categoryName

- DEF: method parameter
- USE: duplication check + Category constructor

| Covered by                                       |
|--------------------------------------------------|
| createCategory_shouldSaveCategory_successfully   |
| createCategory_shouldThrow_whenDuplicateCategory |

---

### 🔹 user

- DEF: validationService.getUserIfExists(userId)
- USE: new Category(categoryName, user)

| Covered by                                     |
|------------------------------------------------|
| createCategory_shouldSaveCategory_successfully |

---

### 🔹 category

- DEF: new Category(categoryName, user)
- USE: categoryRepository.save(category)

| Covered by                                     |
|------------------------------------------------|
| createCategory_shouldSaveCategory_successfully |

---

## 4. DU Analysis - getUserCategories()

---

### 🔹 userId

- DEF: parameter
- USE: validationService.getUserIfExists(userId)
- USE: categoryRepository.findByUserId(userId)

| Covered by                              |
|-----------------------------------------|
| getUserCategories_shouldReturnList      |
| getUserCategories_shouldReturnEmptyList |

---

### 🔹 categories (repository result)

- DEF: repository.findByUserId(userId)
- USE: stream().map(Category::getName)

| Covered by                              |
|-----------------------------------------|
| getUserCategories_shouldReturnList      |
| getUserCategories_shouldReturnEmptyList |

---

### 🔹 categoryName (mapped output)

- DEF: Category entity field
- USE: returned String list

| Covered by                         |
|------------------------------------|
| getUserCategories_shouldReturnList |

---

## 5. Control Flow Coverage Summary

### createCategory()

| Path                         | Covered |
|------------------------------|---------|
| Valid creation               | ✔       |
| Duplicate category exception | ✔       |

---

### getUserCategories()

| Path           | Covered |
|----------------|---------|
| Non-empty list | ✔       |
| Empty list     | ✔       |

---

## 6. DU Coverage Conclusion

CategoryService is fully covered under All-DU-Paths criteria using existing unit tests.

No additional unit tests required.

---

## 7. Final Note

This service mainly validates:

- exception flow (duplication)
- simple repository delegation
- stream mapping logic

So DU coverage is straightforward and already complete.