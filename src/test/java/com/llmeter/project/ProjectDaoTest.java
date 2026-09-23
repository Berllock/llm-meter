package com.llmeter.project;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProjectDaoTest {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private EntityManager entityManager;

    //Validate data persistence in database
    @Test
    void shouldPersistAndFindProject() {
        final Project p = new Project("ClaudeMeter");

        UUID originalUuid = p.getUuid();
        String originalName = p.getName();
        ProjectStatus originalStatus = p.getStatus();

        projectDao.save(p);

        entityManager.flush();
        entityManager.clear();


        final Project persistedProject = projectDao.findByUuid(originalUuid).orElseThrow();

        assertNotNull(persistedProject.getId());
        assertEquals(originalUuid, persistedProject.getUuid());
        assertEquals(originalName, persistedProject.getName());
        assertEquals(originalStatus, persistedProject.getStatus());
        assertNotNull(persistedProject.getCreatedAt());
    }
}