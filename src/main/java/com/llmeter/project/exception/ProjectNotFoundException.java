package com.llmeter.project;

import java.util.UUID;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException(UUID uuid) {
        super("Project not found: " + uuid);
    }
}