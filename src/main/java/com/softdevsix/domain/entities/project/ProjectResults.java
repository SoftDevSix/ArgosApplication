package com.softdevsix.domain.entities.project;

import com.softdevsix.domain.entities.coverage.ProjectCoverageResult;
import com.softdevsix.domain.entities.staticanalysis.CodeAnalysisResult;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ProjectResults {
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID projectId;
    private Status status;
    @Builder.Default
    private ProjectCoverageResult coverageResult = ProjectCoverageResult.builder().build();
    @Builder.Default
    private CodeAnalysisResult codeAnalysisResult = CodeAnalysisResult.builder().build();
}
