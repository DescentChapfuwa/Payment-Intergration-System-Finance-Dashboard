CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender_id BIGINT NOT NULL,
    reference VARCHAR(255) NOT NULL UNIQUE,
    idempotency_key VARCHAR(255) UNIQUE,
    receiver_id BIGINT NOT NULL,
    amount DECIMAL(19,2) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_payments_sender
        FOREIGN KEY (sender_id)
        REFERENCES users(id),

    CONSTRAINT fk_payments_receiver
        FOREIGN KEY (receiver_id)
        REFERENCES users(id),

    CONSTRAINT chk_payment_amount
        CHECK (amount > 0)
);

CREATE INDEX idx_payments_sender_id
    ON payments(sender_id);

CREATE INDEX idx_payments_receiver_id
    ON payments(receiver_id);

CREATE INDEX idx_payments_status
    ON payments(status);

CREATE INDEX idx_payments_created_at
    ON payments(created_at);