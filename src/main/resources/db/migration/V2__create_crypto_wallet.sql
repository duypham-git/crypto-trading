CREATE TABLE crypto_wallet (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    version BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    balance_type VARCHAR(30) NOT NULL,
    balance DECIMAL(19,8) NOT NULL,
    updated_at DATETIME NOT NULL,

    UNIQUE INDEX uidx_crypto_wallet_user_id_balance_type (`user_id`,`balance_type`)
);