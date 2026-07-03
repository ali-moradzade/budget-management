CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL
);

CREATE TABLE friends (
                         id BIGSERIAL PRIMARY KEY,
                         user_id BIGINT NOT NULL,
                         friend_name VARCHAR(255) NOT NULL,
                         friend_phone VARCHAR(50),

                         CONSTRAINT fk_friends_user
                             FOREIGN KEY (user_id)
                                 REFERENCES users(id)
                                 ON DELETE CASCADE
);

CREATE TABLE categories (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            user_id BIGINT NOT NULL,

                            CONSTRAINT fk_categories_user
                                FOREIGN KEY (user_id)
                                    REFERENCES users(id)
                                    ON DELETE CASCADE
);

CREATE TABLE expenses (
                          id BIGSERIAL PRIMARY KEY,
                          user_id BIGINT NOT NULL,
                          category_id BIGINT,
                          amount NUMERIC(19, 2) NOT NULL,
                          description TEXT,
                          expense_date DATE NOT NULL,

                          CONSTRAINT fk_expenses_user
                              FOREIGN KEY (user_id)
                                  REFERENCES users(id)
                                  ON DELETE CASCADE,

                          CONSTRAINT fk_expenses_category
                              FOREIGN KEY (category_id)
                                  REFERENCES categories(id)
                                  ON DELETE SET NULL
);

CREATE TABLE expense_participants (
                                      id BIGSERIAL PRIMARY KEY,
                                      expense_id BIGINT NOT NULL,
                                      friend_id BIGINT NOT NULL,
                                      share_amount NUMERIC(19, 2) NOT NULL,
                                      is_paid BOOLEAN NOT NULL DEFAULT FALSE,

                                      CONSTRAINT fk_expense_participants_expense
                                          FOREIGN KEY (expense_id)
                                              REFERENCES expenses(id)
                                              ON DELETE CASCADE,

                                      CONSTRAINT fk_expense_participants_friend
                                          FOREIGN KEY (friend_id)
                                              REFERENCES friends(id)
                                              ON DELETE CASCADE
);