package com.llmeter.project.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.llmeter.project.domain.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Project save (Project project) {
        entityManager.persist(project);
        return project;
    }

    public Optional<Project> findByUuid(UUID uuid) {
        return entityManager.createQuery(
                """
                SELECT p
                FROM Project p
                WHERE p.uuid = :uuid
                """,
                Project.class
        )
                .setParameter("uuid", uuid)
                .getResultList()
                .stream()
                .findFirst();
    }

    public List<Project> findAll() {
        return entityManager.createQuery(
            """
            SELECT p
            FROM Project p
            ORDER BY p.createdAt DESC
            """,
            Project.class
        ).getResultList();
    }

    public void delete(Project project) {
        entityManager.remove(project);
    }

}
