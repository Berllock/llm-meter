package com.llmeter.project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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
}
