CREATE TABLE idempotency_keys (
    idempotency_key VARCHAR(255) PRIMARY KEY,
    endpoint VARCHAR(255) NOT NULL,
    response_reference VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_idempotency_endpoint_key
        UNIQUE (idempotency_key, endpoint)
);

CREATE INDEX idx_idempotency_keys_created_at
    ON idempotency_keys(created_at);