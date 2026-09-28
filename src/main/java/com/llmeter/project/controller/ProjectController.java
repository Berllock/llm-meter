package com.llmeter.project.controller;

import java.net.URI;
import java.util.UUID;

import com.llmeter.project.controller.dto.ProjectRequest;
import com.llmeter.project.controller.dto.ProjectResponse;
import com.llmeter.project.domain.Project;
import com.llmeter.project.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> create (
            @Valid @RequestBody ProjectRequest request) {

        final Project project = projectService.create(request.name());

        final ProjectResponse response = ProjectResponse.from(project);

        final URI location = URI.create(
                "/api/v1/projects/" + project.getUuid());

        return ResponseEntity
                .created(location)
                .body(response);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProjectResponse> get (
        @PathVariable UUID uuid) {

            final Project project = projectService.get(uuid);
            final ProjectResponse response = ProjectResponse.from(project);

            return ResponseEntity.ok(response);
        }

    @PatchMapping("/{uuid}/deactivate")
    public ResponseEntity<Void> deactivate (
        @PathVariable UUID uuid) {

            projectService.deactivate(uuid);

            return ResponseEntity.noContent().build();
        }

    @PatchMapping("/{uuid}/activate")
    public ResponseEntity<Void> activate (
        @PathVariable UUID uuid) {

            projectService.activate(uuid);

            return ResponseEntity.noContent().build();
        }
}