# DU Analysis - createExpense()

| Variable          | Definition                            | Use                                              |
|-------------------|---------------------------------------|--------------------------------------------------|
| userId            | Controller param                      | all service calls                                |
| request           | Controller param                      | validate(), category(), participants(), amount() |
| user              | ValidationService.getUserIfExists     | Expense constructor                              |
| category          | ValidationService.getCategoryIfExists | Expense constructor                              |
| participants      | request.participants()                | null check, loop                                 |
| participantId     | loop variable                         | getFriendIfExists                                |
| friends           | new ArrayList                         | loop, size, isEmpty                              |
| expense           | new Expense                           | repository.save, participant creation            |
| participantsCount | computed                              | share calculation                                |
| shareAmount       | computed                              | ExpenseParticipant                               |
| participant       | new ExpenseParticipant                | save                                             |