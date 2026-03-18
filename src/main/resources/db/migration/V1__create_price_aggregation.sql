CREATE TABLE price_aggregation (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    version BIGINT NOT NULL,
    symbol VARCHAR(10) NOT NULL,
    best_bid_price DECIMAL(19,8) NOT NULL,
    best_ask_price DECIMAL(19,8) NOT NULL,
    updated_at DATETIME NOT NULL,

    UNIQUE INDEX uidx_price_aggregation_symbol (`symbol`)
);