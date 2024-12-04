package com.softdevsix.application.services;

import com.softdevsix.application.mappers.requests.ProjectCreateRequest;
import com.softdevsix.application.mappers.requests.ProjectUpdateRequest;
import com.softdevsix.application.mappers.responses.ProjectResponse;
import com.softdevsix.domain.entities.project.Project;

import java.util.Optional;
import java.util.UUID;

public interface IProjectService {
    Optional<Project> getProjectById(UUID projectId);
    ProjectResponse createProject(ProjectCreateRequest projectCreateRequest);
    ProjectResponse updateProject(UUID projectId, ProjectUpdateRequest projectUpdateRequest);
}
