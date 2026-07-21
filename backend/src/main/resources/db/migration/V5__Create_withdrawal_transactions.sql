CREATE TABLE withdrawal_transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reference CHAR(36) NOT NULL UNIQUE,
    amount DECIMAL(19,2) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    provider VARCHAR(100) NOT NULL,
    provider_reference VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP,
    user_id BIGINT NOT NULL,

    CONSTRAINT fk_withdrawal_transactions_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT chk_withdrawal_amount
        CHECK (amount > 0)
);

CREATE INDEX idx_withdrawal_transactions_user_id
    ON withdrawal_transactions(user_id);

CREATE INDEX idx_withdrawal_transactions_status
    ON withdrawal_transactions(status);

CREATE INDEX idx_withdrawal_transactions_provider_reference
    ON withdrawal_transactions(provider_reference);

CREATE INDEX idx_withdrawal_transactions_created_at
    ON withdrawal_transactions(created_at);