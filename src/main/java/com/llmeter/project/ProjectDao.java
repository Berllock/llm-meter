package com.llmeter.project;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProjectDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Project save (Project project) {
        entityManager.persist(project);
        return project;
    }

    public Optional<Project> findById(Long id) {
        return entityManager.createQuery(
                """
                SELECT p
                FROM Project p
                WHERE p.uuid = :uuid
                """,
                Project.class
        )
                .setParameter("uuid", id)
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
