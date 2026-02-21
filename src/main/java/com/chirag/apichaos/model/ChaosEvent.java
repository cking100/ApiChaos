package com.chirag.apichaos.model;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ChaosEvent extends BaseEntity {
    
    private String targetServiceId;
    private ChaosType type;
    private ChaosStatus status;
    private String initiatedBy;
    private Instant startedAt;
    private Instant endedAt;
    private String errorMessage;
    private Map<String, Object> parameters;
    private ChaosResult result;
    
    @Builder
    public ChaosEvent(String targetServiceId, ChaosType type, String initiatedBy,
                      Map<String, Object> parameters) {
        super();
        this.targetServiceId = targetServiceId;
        this.type = type;
        this.initiatedBy = initiatedBy;
        this.parameters = parameters;
        this.status = ChaosStatus.PENDING;
        this.startedAt = Instant.now();
    }
    
    public void markStarted() {
        this.status = ChaosStatus.RUNNING;
        this.startedAt = Instant.now();
    }
    
    public void markCompleted(ChaosResult result) {
        this.status = ChaosStatus.COMPLETED;
        this.result = result;
        this.endedAt = Instant.now();
        markUpdated();
    }
    
    public void markFailed(String error) {
        this.status = ChaosStatus.FAILED;
        this.errorMessage = error;
        this.endedAt = Instant.now();
        markUpdated();
    }
    
    public Duration getDuration() {
        if (startedAt == null || endedAt == null) return Duration.ZERO;
        return Duration.between(startedAt, endedAt);
    }
    
    public enum ChaosType {
        POD_KILL,         
        POD_FAILURE,       
        NETWORK_DELAY,     
        NETWORK_PARTITION, 
        CPU_STRESS,        
        MEMORY_STRESS,     
        SCALE_UP,          
        SCALE_DOWN,        
        BLACKHOLE         
    }
    
    public enum ChaosStatus {
        PENDING, RUNNING, COMPLETED, FAILED, CANCELLED
    }
    
    @Getter
    @Builder
    public static class ChaosResult {
        private boolean success;
        private String details;
        private Map<String, Object> metrics;
        private String affectedPod;
    }
}