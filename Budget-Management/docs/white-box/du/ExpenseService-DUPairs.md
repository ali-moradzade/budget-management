| Variable          | Type        | Scope  |
|-------------------|-------------|--------|
| userId            | input param | method |
| request           | input param | method |
| user              | local       | method |
| category          | local       | method |
| friends           | local list  | method |
| participantId     | loop var    | loop   |
| expense           | local       | method |
| participantsCount | local       | block  |
| shareAmount       | local       | block  |
| friend            | loop var    | loop   |

| Line | Variable          | Type  | Description            |
|------|-------------------|-------|------------------------|
| 15   | userId            | param | method input           |
| 15   | request           | param | method input           |
| 16   | user              | def   | fetched user           |
| 17   | category          | def   | fetched category       |
| 19   | friends           | def   | new ArrayList          |
| 21   | participantId     | def   | loop variable          |
| 25   | expense           | def   | new Expense object     |
| 29   | participantsCount | def   | computed               |
| 30   | shareAmount       | def   | computed               |
| 33   | friend            | def   | loop variable          |
| 34   | participant       | def   | new ExpenseParticipant |

| Line | Variable             | Type  | Usage                |
|------|----------------------|-------|----------------------|
| 16   | userId               | p-use | validation call      |
| 17   | request.category     | p-use | category lookup      |
| 20   | request.participants | p-use | loop condition       |
| 21   | participantId        | p-use | friend lookup        |
| 25   | request.amount       | c-use | expense creation     |
| 25   | request.description  | c-use | expense creation     |
| 26   | user                 | c-use | expense creation     |
| 26   | category             | c-use | expense creation     |
| 29   | friends              | c-use | size calculation     |
| 30   | request.amount       | c-use | division             |
| 30   | participantsCount    | c-use | division             |
| 33   | friends              | p-use | loop                 |
| 34   | shareAmount          | c-use | participant creation |
| 34   | expense              | c-use | participant creation |
| 34   | friend               | c-use | participant creation |

| Variable          | Def Line | Use Line |
|-------------------|----------|----------|
| userId            | 15       | 16       |
| request           | 15       | 17       |
| request           | 15       | 20       |
| request           | 15       | 25       |
| request           | 15       | 30       |
| user              | 16       | 26       |
| category          | 17       | 26       |
| friends           | 19       | 29       |
| friends           | 19       | 33       |
| participantId     | 21       | 21       |
| expense           | 25       | 34       |
| participantsCount | 29       | 30       |
| shareAmount       | 30       | 34       |
| friend            | 33       | 34       |
