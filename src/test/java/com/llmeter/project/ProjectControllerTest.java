package com.llmeter.project;

import com.llmeter.project.controller.ProjectController;
import com.llmeter.project.domain.Project;
import com.llmeter.project.exception.ProjectNotFoundException;
import com.llmeter.project.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static javax.management.Query.value;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private ProjectService projectService;

    //Test to verify if the project is created at the POST endpoint
    @Test
    void shouldCreateProject() throws Exception {

        Project project = new Project("ClaudeMeter");

        when(projectService.create("ClaudeMeter"))
                .thenReturn(project);

        mvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "name": "ClaudeMeter"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name")
                        .value("ClaudeMeter"))
                .andExpect(jsonPath("$.status")
                        .value("ACTIVE"))
                .andExpect(jsonPath("$.uuid")
                        .value(project.getUuid().toString()))
                .andExpect(header().string(
                        "Location",
                        "/api/v1/projects/" + project.getUuid()
                ));
    }

    //Test to verify the response with a blank request name
    @Test
    void shouldReturnBadRequestWhenNameIsEmpty()
            throws Exception {

        mvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "name": ""
                        }
                        """))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(projectService);
    }

    //Test to verify the header response
    @Test
    void shouldReturnLocationHeader() throws Exception {

        Project project = new Project("ClaudeMeter");

        when(projectService.create("ClaudeMeter"))
                .thenReturn(project);

        mvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "name": "ClaudeMeter"
                    }
                    """))
                .andExpect(status().isCreated())
                .andExpect(header().string(
                        "Location",
                        "/api/v1/projects/" + project.getUuid()
                ));
    }

    // Test that the GET endpoint returns an existing project
    @Test
    void shouldReturnResponseGet() throws Exception {

        Project project = new Project("ClaudeMeter");

        when(projectService.get(project.getUuid()))
                .thenReturn(project);

        mvc.perform(get("/api/v1/projects/{uuid}", project.getUuid()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value("ClaudeMeter"))
                .andExpect(jsonPath("$.status")
                        .value("ACTIVE"))
                .andExpect(jsonPath("$.uuid")
                        .value(project.getUuid().toString()))
                .andExpect(jsonPath("$.createdAt")
                        .exists());
    }

    // Test that the GET endpoint returns a 404 when the project is not found
    @Test
    void shouldReturnNotFoundWhenProjectNotFound() throws Exception {

        UUID randomUuid = UUID.randomUUID();

        when(projectService.get(randomUuid))
                .thenThrow(
                        new ProjectNotFoundException(randomUuid)
                );

        mvc.perform(get("/api/v1/projects/{uuid}", randomUuid))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail")
                        .value("Project not found: " + randomUuid));

    }
}
