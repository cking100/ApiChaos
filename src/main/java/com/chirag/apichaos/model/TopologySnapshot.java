package com.chirag.apichaos.model;
import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class TopologySnapshot extends BaseEntity {
    
    private List<ServiceNode> services;
    private List<ServiceEdge> edges;
    private Map<String, Object> metadata;
    private String clusterVersion;
    
    @Builder
    public TopologySnapshot(List<ServiceNode> services, List<ServiceEdge> edges,
                           String clusterVersion) {
        super();
        this.services = services;
        this.edges = edges;
        this.clusterVersion = clusterVersion;
    }
    
    @Getter
    @Builder
    public static class ServiceEdge {
        private String sourceId;
        private String targetId;
        private EdgeType type;
        private Map<String, String> metadata;
        
        public enum EdgeType {
            HTTP, GRPC, DATABASE, CACHE, MESSAGE_QUEUE, UNKNOWN
        }
    }
}
