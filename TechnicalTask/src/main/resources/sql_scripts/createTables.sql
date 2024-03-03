CREATE TABLE IF NOT EXISTS bank_accounts
(
    id      SERIAL PRIMARY KEY,
    balance REAL   NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    id              SERIAL PRIMARY KEY,
    username        VARCHAR(255) NOT NULL UNIQUE,
    password        VARCHAR(255) NOT NULL,
    phone           VARCHAR(255) UNIQUE,
    email           VARCHAR(255) UNIQUE,
    date_of_birth   DATE,
    full_name       VARCHAR(255),
    top_balance     REAL         NOT NULL,
    bank_account_id BIGINT,
    FOREIGN KEY (bank_account_id) REFERENCES bank_accounts(id) ON DELETE CASCADE
);
