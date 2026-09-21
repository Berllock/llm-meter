package com.llmeter.project;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService {

    private final ProjectDao projectDao;

    public ProjectService(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Transactional
    public Project create(String name) {
        Project project = new Project(name);
        return projectDao.save(project);
    }



}