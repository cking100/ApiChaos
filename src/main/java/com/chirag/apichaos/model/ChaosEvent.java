package com.chirag.apichaos.model;

import java.time.Duration;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "chaos_events")
public class ChaosEvent extends BaseEntity {

    @Column(nullable = false)
    private String targetServiceId;

    @Column(nullable = false)
    private String targetServiceName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChaosType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChaosStatus status;

    @Column(nullable = false)
    private String initiatedBy;

    private Instant startedAt;
    private Instant endedAt;

    @Column(length = 2000)
    private String errorMessage;

    private boolean resultSuccess;

    @Column(length = 1000)
    private String resultDetails;

    public ChaosEvent() {}

    public String getTargetServiceId() { return targetServiceId; }
    public void setTargetServiceId(String v) { this.targetServiceId = v; }

    public String getTargetServiceName() { return targetServiceName; }
    public void setTargetServiceName(String v) { this.targetServiceName = v; }

    public ChaosType getType() { return type; }
    public void setType(ChaosType v) { this.type = v; }

    public ChaosStatus getStatus() { return status; }
    public void setStatus(ChaosStatus v) { this.status = v; }

    public String getInitiatedBy() { return initiatedBy; }
    public void setInitiatedBy(String v) { this.initiatedBy = v; }

    public Instant getStartedAt() { return startedAt; }
    public void setStartedAt(Instant v) { this.startedAt = v; }

    public Instant getEndedAt() { return endedAt; }
    public void setEndedAt(Instant v) { this.endedAt = v; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String v) { this.errorMessage = v; }

    public boolean isResultSuccess() { return resultSuccess; }
    public void setResultSuccess(boolean v) { this.resultSuccess = v; }

    public String getResultDetails() { return resultDetails; }
    public void setResultDetails(String v) { this.resultDetails = v; }

    public void markStarted() {
        this.status = ChaosStatus.RUNNING;
        this.startedAt = Instant.now();
    }

    public void markCompleted(boolean success, String details) {
        this.status = ChaosStatus.COMPLETED;
        this.resultSuccess = success;
        this.resultDetails = details;
        this.endedAt = Instant.now();
        markUpdated();
    }

    public void markFailed(String error) {
        this.status = ChaosStatus.FAILED;
        this.errorMessage = error;
        this.endedAt = Instant.now();
        markUpdated();
    }

    public long getDurationMs() {
        if (startedAt == null || endedAt == null) return 0;
        return Duration.between(startedAt, endedAt).toMillis();
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String targetServiceId;
        private String targetServiceName;
        private ChaosType type;
        private ChaosStatus status = ChaosStatus.PENDING;
        private String initiatedBy;

        public Builder targetServiceId(String v) { this.targetServiceId = v; return this; }
        public Builder targetServiceName(String v) { this.targetServiceName = v; return this; }
        public Builder type(ChaosType v) { this.type = v; return this; }
        public Builder status(ChaosStatus v) { this.status = v; return this; }
        public Builder initiatedBy(String v) { this.initiatedBy = v; return this; }

        public ChaosEvent build() {
            ChaosEvent e = new ChaosEvent();
            e.targetServiceId = this.targetServiceId;
            e.targetServiceName = this.targetServiceName;
            e.type = this.type;
            e.status = this.status;
            e.initiatedBy = this.initiatedBy;
            return e;
        }
    }

    public enum ChaosType {
        POD_KILL, POD_FAILURE, NETWORK_DELAY, NETWORK_PARTITION,
        CPU_STRESS, MEMORY_STRESS, SCALE_UP, SCALE_DOWN, BLACKHOLE
    }

    public enum ChaosStatus {
        PENDING, RUNNING, COMPLETED, FAILED, CANCELLED
    }
}