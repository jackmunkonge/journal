package com.solirius.journal.Service;

import com.solirius.journal.model.Project;
import com.solirius.journal.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Optional<Project> getProject(Integer projectId) {
        return projectRepository.findById(projectId);
    }

    public Optional<Project> getProject(String projectName) {
        return projectRepository.findByName(projectName);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAllByOrderByProjectIdAsc();
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project destroyProject(Project project) {
        projectRepository.delete(project);
        return project;
    }
}
