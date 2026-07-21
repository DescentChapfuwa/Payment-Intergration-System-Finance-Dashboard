CREATE TABLE wallets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    balance DECIMAL(19,2) NOT NULL DEFAULT 0.00,
    reserved_balance DECIMAL(19,2) NOT NULL DEFAULT 0.00,
    currency VARCHAR(10) NOT NULL,
    user_id BIGINT NOT NULL UNIQUE,

    CONSTRAINT fk_wallets_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT chk_wallet_balance
        CHECK (balance >= 0),

    CONSTRAINT chk_wallet_reserved_balance
        CHECK (reserved_balance >= 0)
);

CREATE INDEX idx_wallets_currency
    ON wallets(currency);