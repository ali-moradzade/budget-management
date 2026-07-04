# FriendService - DU Analysis

## Overview

This document presents DU (Definition-Use) analysis for FriendService.
The service handles friend creation and retrieval for a user with validation checks.

---

## Method: addFriend(Long userId, CreateFriendRequest request)

### Variables

- userId (parameter)
- request (parameter object)
- friendName (from request)
- friendPhone (from request)
- user (validated user entity)
- friend (entity object)

---

## Definitions

- userId: method input
- request: method input
- friendName: extracted from request
- friendPhone: extracted from request
- user: result of `validationService.getUserIfExists(userId)`
- friend: `new Friend(friendName, friendPhone, user)`

---

## Uses

- userId: used in validation check
- request.friendName: used in duplication check
- request.friendName: used in entity creation
- request.friendPhone: used in entity creation
- user: used as owner of Friend entity
- friend: used in repository save

---

## DU Pairs

| Variable    | Definition         | Use               | Path                               |
|-------------|--------------------|-------------------|------------------------------------|
| userId      | parameter entry    | user validation   | entry → getUserIfExists            |
| request     | parameter entry    | duplication check | entry → checkFriendNameDuplication |
| friendName  | request extraction | duplication check | entry → validation                 |
| friendPhone | request extraction | entity creation   | entry → new Friend                 |
| user        | validation result  | entity creation   | validation → new Friend            |
| friend      | entity creation    | repository save   | new → save                         |

---

## Method: getUserFriends(Long userId)

### Variables

- userId
- friends (list from repository)
- friend (stream element)

---

## DU Pairs

| Variable | Definition        | Use              | Path                    |
|----------|-------------------|------------------|-------------------------|
| userId   | parameter entry   | validation check | entry → getUserIfExists |
| friends  | repository result | stream mapping   | repository → stream     |
| friend   | stream element    | DTO mapping      | stream → FriendResponse |

---

## Summary

This service ensures:

- user existence validation before operations
- duplication prevention for friend creation
- proper mapping of Friend entities to response DTOs