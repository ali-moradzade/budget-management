# CategoryService - DU Analysis

## Overview

This document presents DU (Definition-Use) analysis for CategoryService.
The service handles category creation and retrieval per user with duplication validation.

---

## Method: createCategory(Long userId, String categoryName)

### Variables

- userId (parameter)
- categoryName (parameter)
- user (validated entity)
- category (entity object)

---

## Definitions

- userId: method input
- categoryName: method input
- user: result of `validationService.getUserIfExists(userId)`
- category: `new Category(categoryName, user)`

---

## Uses

- userId: used in user validation
- categoryName: used in duplication check
- categoryName: used in entity creation
- user: used as owner of Category
- category: used in repository save

---

## DU Pairs

| Variable     | Definition        | Use               | Path                                 |
|--------------|-------------------|-------------------|--------------------------------------|
| userId       | parameter entry   | validation        | entry → getUserIfExists              |
| categoryName | parameter entry   | duplication check | entry → checkCategoryNameDuplication |
| categoryName | parameter entry   | entity creation   | entry → new Category                 |
| user         | validation result | entity creation   | validation → new Category            |
| category     | entity creation   | repository save   | new → save                           |

---

## Method: getUserCategories(Long userId)

### Variables

- userId
- categories (repository result)
- category (stream element)

---

## DU Pairs

| Variable   | Definition        | Use               | Path                    |
|------------|-------------------|-------------------|-------------------------|
| userId     | parameter entry   | validation        | entry → getUserIfExists |
| categories | repository result | stream processing | repository → stream     |
| category   | stream element    | mapping           | stream → getName        |

---

## Summary

This service ensures:

- user existence validation
- duplicate category prevention
- clean transformation of Category entities into string responses