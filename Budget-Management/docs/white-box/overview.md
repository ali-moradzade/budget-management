# White-Box Testing Overview

This document describes the white-box testing approach for the Budget Management System.

## Scope

White-box testing is applied to service-layer components:

- ValidationService
- UserService
- FriendService
- CategoryService
- ExpenseService
- ExpenseParticipantService

## Techniques Used

### 1. Control Flow Analysis (ICFG)

Each service is modeled using an Interprocedural Control Flow Graph (ICFG) to represent:

- method invocations
- branching logic
- repository interactions

### 2. Data Flow Testing

We apply DU (Definition-Use) testing:

- Definition: variable assignment or retrieval
- Use: variable usage in computation or condition
- DU Pair: valid path between definition and use without redefinition

### 3. Coverage Criterion

We use:

- All-DU-Paths Coverage

Each DU pair must be covered by at least one test case.

## Output Artifacts

- ICFG diagrams (.puml)
- DU analysis per service
- Test mapping document