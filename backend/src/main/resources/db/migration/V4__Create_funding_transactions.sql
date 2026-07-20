CREATE TABLE funding_transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reference CHAR(36) NOT NULL UNIQUE,
    user_id BIGINT,
    poll_url VARCHAR(255),
    amount DECIMAL(19,2) NOT NULL,
    provider VARCHAR(100) NOT NULL,
    provider_reference VARCHAR(255),
    status VARCHAR(50),
    created_at TIMESTAMP,
    completed_at TIMESTAMP,

    CONSTRAINT fk_funding_transactions_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);

CREATE INDEX idx_funding_transactions_user_id
    ON funding_transactions(user_id);

CREATE INDEX idx_funding_transactions_status
    ON funding_transactions(status);

CREATE INDEX idx_funding_transactions_provider
    ON funding_transactions(provider);

CREATE INDEX idx_funding_transactions_created_at
    ON funding_transactions(created_at);