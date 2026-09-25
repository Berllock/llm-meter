package com.llmeter.project;

import java.time.Instant;
import java.util.UUID;

public record ProjectResponse(
        UUID uuid,
        String name,
        ProjectStatus status,
        Instant createdAt) {

    public static ProjectResponse from(final Project project) {
        return new ProjectResponse(
                project.getUuid(),
                project.getName(),
                project.getStatus(),
                project.getCreatedAt()
        );
    }

}