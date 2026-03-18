package com.me.crypto.trading.common.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

@MappedSuperclass
public abstract class BaseEntity {
    @Version
    private long version;
}
