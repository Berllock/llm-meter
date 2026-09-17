package com.llmeter.project;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true, updatable = false)
    private UUID uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected Project() {
    }

    public Project(String name) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.status = ProjectStatus.ACTIVE;
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void rename(String name) {
        this.name = name;
    }

    public void activate(){
        this.status = ProjectStatus.ACTIVE;
    }

    public void deactivate() {
        this.status = ProjectStatus.INACTIVE;
    }
}
