# UserService - DU Analysis

## Overview

This document presents DU (Definition-Use) analysis for UserService.
The service contains user creation logic with duplication validation.

---

## Method: createUser(String username)

### Variables

- username (parameter)
- user (entity object)
- savedUser (repository result)

---

## Definitions

- username: method input
- user: `new User(username)`
- savedUser: result of `userRepository.save(user)`

---

## Uses

- username: used in duplication check
- username: used in User constructor
- user: used in repository save
- savedUser: used to return generated ID

---

## DU Pairs

| Variable  | Definition             | Use               | Path                             |
|-----------|------------------------|-------------------|----------------------------------|
| username  | parameter entry        | duplication check | entry → checkUserNameDuplication |
| username  | parameter entry        | entity creation   | entry → new User                 |
| user      | new User(username)     | repository save   | new → save                       |
| savedUser | repository save result | return id         | save → return                    |

---

## Summary

This service ensures:

- username uniqueness is validated before creation
- user entity is created and persisted
- generated ID is returned as output