CREATE TABLE customers (
    id VARCHAR(50) PRIMARY KEY,

    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20),
    gender VARCHAR(20),
    dob DATE,
    status VARCHAR(50),
    kyc_status VARCHAR(50),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE users (
    id VARCHAR(50) PRIMARY KEY,

    customer_id VARCHAR(50) NULL, -- ✅ FIXED (IMPORTANT)

    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    enabled BOOLEAN NOT NULL,

    CONSTRAINT fk_user_customer
        FOREIGN KEY (customer_id)
        REFERENCES customers(id)
        ON DELETE SET NULL -- ✅ FIXED
);

CREATE TABLE accounts (
    account_id VARCHAR(50) PRIMARY KEY,
    customer_id VARCHAR(50) NOT NULL,

    account_type VARCHAR(50) NOT NULL,
    balance DECIMAL(19,2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_account_customer
        FOREIGN KEY (customer_id)
        REFERENCES customers(id)
        ON DELETE CASCADE
);

CREATE TABLE transactions (
    transaction_id VARCHAR(50) PRIMARY KEY,
    from_account_id VARCHAR(50),
    to_account_id VARCHAR(50),

    amount DECIMAL(19,2) NOT NULL,

    type ENUM('DEPOSIT', 'WITHDRAW', 'TRANSFER') NOT NULL,
    status ENUM('SUCCESS', 'FAILED', 'PENDING') NOT NULL,

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT fk_from_account
        FOREIGN KEY (from_account_id)
        REFERENCES accounts(account_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_to_account
        FOREIGN KEY (to_account_id)
        REFERENCES accounts(account_id)
        ON DELETE CASCADE
);