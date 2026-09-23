package com.llmeter.project;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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

}
