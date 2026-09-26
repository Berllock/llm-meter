package com.llmeter.project.controller.dto;

import java.time.Instant;
import java.util.UUID;

import com.llmeter.project.domain.Project;
import com.llmeter.project.domain.ProjectStatus;

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
