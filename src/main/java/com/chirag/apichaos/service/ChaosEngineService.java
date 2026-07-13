package com.chirag.apichaos.service;
import java.util.Map;

import com.chirag.apichaos.model.ChaosEvent;

import reactor.core.publisher.Mono;

public interface ChaosEngineService {
    Mono<ChaosEvent> executeAttack(String serviceId, ChaosEvent.ChaosType type, 
                                   Map<String, Object> parameters, String userId);
    
    
    boolean validateAttack(String serviceId, ChaosEvent.ChaosType type);
    
    Mono<ChaosEvent> getRunningAttack(String serviceId);

    Mono<Boolean> abortAttack(String eventId);

    Mono<java.util.List<ChaosEvent>> getAttackHistory(String serviceId);
}
