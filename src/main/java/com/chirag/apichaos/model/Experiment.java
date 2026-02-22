package com.chirag.apichaos.model;
import java.time.Duration;
import java.util.List;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Experiment extends BaseEntity {
    
    private String name;
    private String description;
    private String hypothesis;
    private List<ChaosEvent.ChaosType> attackSequence;
    private String targetServicePattern;  
    private Duration duration;
    private Integer abortThreshold;       
    private ExperimentStatus status;
    private List<String> eventIds;
    private String createdBy;
    
    @Builder
    public Experiment(String name, String description, String hypothesis,
                      List<ChaosEvent.ChaosType> attackSequence,
                      String targetServicePattern, Duration duration,
                      Integer abortThreshold, String createdBy) {
        super();
        this.name = name;
        this.description = description;
        this.hypothesis = hypothesis;
        this.attackSequence = attackSequence;
        this.targetServicePattern = targetServicePattern;
        this.duration = duration;
        this.abortThreshold = abortThreshold;
        this.createdBy = createdBy;
        this.status = ExperimentStatus.DRAFT;
    }
    
    public enum ExperimentStatus {
        DRAFT, SCHEDULED, RUNNING, PAUSED, COMPLETED, ABORTED
    }
}