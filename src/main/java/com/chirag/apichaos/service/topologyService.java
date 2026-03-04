package com.chirag.apichaos.service;
import java.util.List;

import com.chirag.apichaos.model.ServiceNode;
import com.chirag.apichaos.model.TopologySnapshot;

import reactor.core.publisher.Flux;

public interface topologyService {
    
    /**
     * Fetch current state of all services from Kubernetes
     */
    List<ServiceNode> discoverServices();
    
    /**
     * Get specific service by ID
     */
    ServiceNode getService(String serviceId);
    
    /**
     * Stream topology updates in real-time
     */
    Flux<TopologySnapshot> streamTopology();
    
    /**
     * Refresh single service state
     */
    ServiceNode refreshService(String serviceId);
    
    /**
     * Build connection graph between services
     */
    TopologySnapshot buildSnapshot();
}