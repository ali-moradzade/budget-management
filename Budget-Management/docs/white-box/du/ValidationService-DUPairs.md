| Variable     | Scope |
|--------------|-------|
| userId       | param |
| categoryName | param |
| expenseId    | param |
| id           | param |
| friendId     | param |
| username     | param |
| participant  | local |

| Variable     | Def   | Use                 |
|--------------|-------|---------------------|
| userId       | param | repository query    |
| categoryName | param | category lookup     |
| expenseId    | param | expense lookup      |
| id           | param | participant lookup  |
| friendId     | param | friend lookup       |
| username     | param | duplication check   |
| participant  | fetch | validation + return |
