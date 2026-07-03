| Variable    | Type  |
|-------------|-------|
| userId      | param |
| expenseId   | param |
| friendId    | param |
| id          | param |
| participant | local |
| friends     | list  |
| debts       | list  |

| Variable    | Def      | Use               |
|-------------|----------|-------------------|
| userId      | param    | validation        |
| expenseId   | param    | repository lookup |
| friendId    | param    | repository lookup |
| id          | param    | markAsPaid/Unpaid |
| participant | fetch    | update + save     |
| friends     | fetch    | stream mapping    |
| debts       | computed | sum reduction     |
