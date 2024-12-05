package com.softdevsix.application.services;

import com.softdevsix.domain.entities.project.Project;
import com.softdevsix.domain.entities.staticanalysis.CodeAnalysisResult;
import com.softdevsix.domain.repositories.ICodeAnalysisResultRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CodeAnalysisResultService implements ICodeAnalysisResultService {
    private final ICodeAnalysisResultRepository iCodeAnalysisResultRepository;
    private final IProjectService iProjectService;

    public CodeAnalysisResultService(ICodeAnalysisResultRepository iCodeAnalysisResultRepository,
                                     IProjectService iProjectService) {
        this.iCodeAnalysisResultRepository = iCodeAnalysisResultRepository;
        this.iProjectService = iProjectService;

    }

    @Override
    public Optional<CodeAnalysisResult> getProjectRatingById(UUID projectId) {
        Optional<CodeAnalysisResult> codeAnalysisResultOptional = iCodeAnalysisResultRepository.findById(projectId);

        if (codeAnalysisResultOptional.isEmpty()) {
            throw new RuntimeException("Cannot found code analysis for project with id: " + projectId);
        }

        return codeAnalysisResultOptional;
    }

    @Override
    public Optional<CodeAnalysisResult> calculateProjectRating(UUID projectId) {
        Project project = iProjectService.getProjectById(projectId);

        String actualRating = performCodeAnalysis(project);

        CodeAnalysisResult analysisResult = CodeAnalysisResult.builder()
                .actualRating(actualRating)
                .build();

        return Optional.of(iCodeAnalysisResultRepository.save(analysisResult));
    }

    private String performCodeAnalysis(Project project) {
        return "A";
    }

//    public void calculateProjectRating(UUID projectId) {
//        Optional<Project> project = getProjectById(projectId);
//        project.getProjectResults().getCodeAnalysisResult().setActualRating(Rating.A);
//        projectRepository.save(project.get());
//    }
}
