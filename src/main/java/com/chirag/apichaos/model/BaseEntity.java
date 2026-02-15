package com.chirag.apichaos.model;

import java.time.Instant;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseEntity {
    private String id;
    private Instant createdAt;
    private Instant updatedAt;
    
    protected BaseEntity() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
    
    public void markUpdated() {
        this.updatedAt = Instant.now();
    }
}