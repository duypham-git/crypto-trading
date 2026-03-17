package com.me.crypto.trading.common.entity;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseStringIDEntity extends BaseEntity {
    @Id
    private String id;
}
