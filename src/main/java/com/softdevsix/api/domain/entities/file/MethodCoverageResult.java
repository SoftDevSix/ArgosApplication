package com.softdevsix.api.domain.entities.file;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class MethodCoverageResult {
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private float coveragePercentage;
    private Map<Integer, Boolean> statements;
}
