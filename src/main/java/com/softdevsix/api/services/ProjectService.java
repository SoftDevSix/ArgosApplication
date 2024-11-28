package com.softdevsix.api.services;

import com.softdevsix.api.domain.coverage.ProjectCoverageResult;
import com.softdevsix.api.domain.entities.file.File;
import com.softdevsix.api.domain.entities.project.Project;
import com.softdevsix.api.domain.entities.project.ProjectResults;
import com.softdevsix.api.domain.entities.project.Status;
import com.softdevsix.api.domain.staticanalysis.CodeAnalysisResult;
import com.softdevsix.api.domain.staticanalysis.Rating;
import com.softdevsix.api.exceptions.ProjectNotFoundException;
import com.softdevsix.api.repositories.IProjectRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProjectService implements IProjectService {
    private final IProjectRepository PROJECT_REPOSITORY;

    public ProjectService(IProjectRepository projectRepository) {
        this.PROJECT_REPOSITORY = projectRepository;
    }

    public Project getProjectById(UUID projectId) {
        return PROJECT_REPOSITORY.findById(projectId)
                .orElseThrow(() ->
                        new ProjectNotFoundException("Project with Id: " + projectId + " not found")
                );
    }

    public void calculateProjectCoverage(UUID projectId) {
        Project project = getProjectById(projectId);

        float totalCoverage = 0f;

        for(File file : project.getCoveredFiles()) {
            totalCoverage += file.getCoverageResult().getCoveragePercentage();
        }

        totalCoverage /= project.getCoveredFiles().size();
        project.getProjectResults().getCoverageResult().setTotalCoverage(totalCoverage);

        PROJECT_REPOSITORY.save(project);
    }

    public void calculateProjectRating(UUID projectId) {
        Project project = getProjectById(projectId);
        project.getProjectResults().getCodeAnalysisResult().setActualRating(Rating.A);
        PROJECT_REPOSITORY.save(project);
    }

    public void calculateProjectStatus(UUID projectId) {
        Project project = getProjectById(projectId);

        ProjectResults projectResults = project.getProjectResults();

        ProjectCoverageResult coverageResult = projectResults.getCoverageResult();
        boolean coveragePassed = coverageResult.getTotalCoverage() >= coverageResult.getRequiredCoverage();

        CodeAnalysisResult analysisResult = projectResults.getCodeAnalysisResult();
        boolean analysisPassed = analysisResult.getActualRating().compareTo(analysisResult.getExpectedRating()) <= 0;

        if(coveragePassed && analysisPassed) {
            projectResults.setStatus(Status.PASSED);
        } else {
            projectResults.setStatus(Status.FAILED);
        }

        PROJECT_REPOSITORY.save(project);
    }

    public ProjectResults calculateProjectResults(UUID projectId) {
        calculateProjectCoverage(projectId);
        calculateProjectRating(projectId);
        calculateProjectStatus(projectId);

        return getProjectById(projectId).getProjectResults();
    }
}
