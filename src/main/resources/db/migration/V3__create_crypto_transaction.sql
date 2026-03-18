CREATE TABLE crypto_transaction (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    version BIGINT NOT NULL,
    txn_code VARCHAR(50) NOT NULL,
    user_id BIGINT NOT NULL,
    symbol VARCHAR(10) NOT NULL,
    side VARCHAR(10) NOT NULL,
    price DECIMAL(19,8) NOT NULL,
    quantity DECIMAL(19,8) NOT NULL,
    total_amount DECIMAL(19,8) NOT NULL,
    created_at DATETIME NOT NULL,

    UNIQUE INDEX uidx_crypto_transaction_txn_code (`txn_code`),
    INDEX idx_crypto_transaction_user_id_symbol_side (`user_id`, `symbol`, `side`)
);