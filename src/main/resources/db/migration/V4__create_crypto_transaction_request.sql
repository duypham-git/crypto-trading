CREATE TABLE crypto_transaction_request (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    version BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    request_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT uidx_crypto_transaction_request_request_id UNIQUE (`request_id`)
);