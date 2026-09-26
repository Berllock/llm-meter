package com.llmeter.project.service;

import java.util.UUID;

import com.llmeter.project.dao.ProjectDao;
import com.llmeter.project.domain.Project;
import com.llmeter.project.exception.ProjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService {

    private final ProjectDao projectDao;

    public ProjectService(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Transactional
    public Project create(final String name) {

        final Project project = new Project(name);
        return projectDao.save(project);

    }
    
    @Transactional(readOnly = true)
    public Project get(final UUID uuid) {

        return projectDao.findByUuid(uuid)
                .orElseThrow(
                        () -> new ProjectNotFoundException(uuid)
                );
    }



}
