CREATE TABLE crypto_transaction_request (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    version BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    request_id VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,

    UNIQUE INDEX uidx_crypto_transaction_request_request_id (`request_id`)
);