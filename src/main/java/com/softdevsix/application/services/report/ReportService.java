package com.softdevsix.application.services.report;

import com.softdevsix.domain.entities.file.File;
import com.softdevsix.domain.entities.project.Project;
import com.softdevsix.domain.entities.report.Report;
import com.softdevsix.application.mappers.json.ProjectMapper;
import com.softdevsix.domain.repositories.IFileRepository;
import com.softdevsix.domain.repositories.ProjectRepository;
import lombok.Value;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;

@Service
@Value
public class ReportService implements IReportService {

    JsonReportReader reportReader;
    ProjectRepository projectRepository;
    IFileRepository fileRepository;

    public ReportService(JsonReportReader reportReader, ProjectRepository projectRepository, IFileRepository fileRepository) {
        this.reportReader = reportReader;
        this.projectRepository = projectRepository;
        this.fileRepository = fileRepository;
    }

    public void processAndSaveReport(String coverageJson) {
        Report report = reportReader.read(coverageJson);
        saveReportToDatabase(report);
    }

    public void saveReportToDatabase(Report report) {
        ProjectMapper mapper = new ProjectMapper();
        Project project = mapper.handleReport(report);
        projectRepository.save(project);
        fileRepository.saveAll(project.getCoveredFiles());
    }
}

