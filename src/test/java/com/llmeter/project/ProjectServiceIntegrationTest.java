
package com.llmeter.project;

import com.llmeter.project.dao.ProjectDao;
import com.llmeter.project.domain.Project;
import com.llmeter.project.domain.ProjectStatus;
import com.llmeter.project.service.ProjectService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class ProjectServiceIntegrationTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private EntityManager entityManager;

    //Validate data persistence in database
    @Test
    void shouldPersistProjectDeactivation() {

        Project project = new Project("ClaudeMeter");

        projectDao.save(project);

        UUID uuid = project.getUuid();

        entityManager.flush();
        entityManager.clear();

        projectService.deactivate(uuid);

        entityManager.flush();
        entityManager.clear();

        Project reloadedProject = projectDao.findByUuid(uuid)
                .orElseThrow();

        assertEquals(
                ProjectStatus.INACTIVE,
                reloadedProject.getStatus()
        );
    }

    //Validate data persistence in database
    @Test
    void shouldPersistProjectActivation() {
        Project project = new Project("ClaudeMeter");

        projectDao.save(project);

        UUID uuid = project.getUuid();

        entityManager.flush();
        entityManager.clear();

        projectService.deactivate(uuid);

        entityManager.flush();
        entityManager.clear();

        Project inactiveProject = projectDao.findByUuid(uuid)
                .orElseThrow();

        assertEquals(ProjectStatus.INACTIVE, inactiveProject.getStatus());

        entityManager.clear();

        projectService.activate(uuid);

        entityManager.flush();
        entityManager.clear();

        Project reloadedProject = projectDao.findByUuid(uuid)
                .orElseThrow();

        assertEquals(
                ProjectStatus.ACTIVE,
                reloadedProject.getStatus()
        );
    }
}
