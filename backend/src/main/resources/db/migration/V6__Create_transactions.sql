CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(19,2) NOT NULL,
    type VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    wallet_id BIGINT NOT NULL,

    CONSTRAINT fk_transactions_wallet
        FOREIGN KEY (wallet_id)
        REFERENCES wallets(id),

    CONSTRAINT chk_transaction_amount
        CHECK (amount > 0)
);

CREATE INDEX idx_transactions_wallet_id
    ON transactions(wallet_id);

CREATE INDEX idx_transactions_type
    ON transactions(type);

CREATE INDEX idx_transactions_created_at
    ON transactions(created_at);