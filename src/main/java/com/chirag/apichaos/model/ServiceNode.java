package com.chirag.apichaos.model;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ServiceNode extends BaseEntity {
    
    private String name;
    private String namespace;
    private Integer desiredReplicas;
    private Integer availableReplicas;
    private ServiceStatus status;
    private Map<String, String> labels;
    private Map<String, String> annotations;
    private String image;
    private String version;
    
    @Builder
    public ServiceNode(String name, String namespace, Integer desiredReplicas, 
                       Integer availableReplicas, Map<String, String> labels,
                       Map<String, String> annotations, String image, String version) {
        super();
        this.name = name;
        this.namespace = namespace;
        this.desiredReplicas = desiredReplicas;
        this.availableReplicas = availableReplicas;
        this.labels = labels;
        this.annotations = annotations;
        this.image = image;
        this.version = version;
        this.status = determineStatus();
    }
    
    private ServiceStatus determineStatus() {
        if (availableReplicas == null || availableReplicas == 0) {
            return ServiceStatus.DOWN;
        }
        if (availableReplicas < desiredReplicas) {
            return ServiceStatus.DEGRADED;
        }
        return ServiceStatus.HEALTHY;
    }
    
    public void updateReplicas(Integer available) {
        this.availableReplicas = available;
        this.status = determineStatus();
        markUpdated();
    }
    
    public enum ServiceStatus {
        HEALTHY, DEGRADED, DOWN
    }
}