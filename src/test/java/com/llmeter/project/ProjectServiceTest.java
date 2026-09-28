package com.llmeter.project;

import java.util.Optional;
import java.util.UUID;

import com.llmeter.project.dao.ProjectDao;
import com.llmeter.project.domain.Project;
import com.llmeter.project.domain.ProjectStatus;
import com.llmeter.project.exception.ProjectNotFoundException;
import com.llmeter.project.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectDao projectDao;

    @InjectMocks
    private ProjectService projectService;


    @Test
    void shouldCreateProject() {

        when(projectDao.save(any(Project.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        final Project createdProject =
                projectService.create("ClaudeMeter");

        final ArgumentCaptor<Project> captor =
                ArgumentCaptor.forClass(Project.class);

        verify(projectDao).save(captor.capture());

        final Project savedProject = captor.getValue();

        assertSame(createdProject, savedProject);
        assertEquals("ClaudeMeter", savedProject.getName());
        assertEquals(ProjectStatus.ACTIVE, savedProject.getStatus());
        assertNotNull(savedProject.getUuid());
        assertNotNull(savedProject.getCreatedAt());
    }


    @Test
    void shouldReturnExistingProject() {
        final Project project = new Project("ClaudeMeter");
        final UUID uuid = project.getUuid();

        when(projectDao.findByUuid(uuid))
                .thenReturn(Optional.of(project));

        final Project foundProject = projectService.get(uuid);

        assertSame(project, foundProject);
        verify(projectDao).findByUuid(uuid);
    }

    @Test
    void shouldThrowExceptionWhenProjectDoesNotExist() {
        final UUID uuid = UUID.randomUUID();

        when(projectDao.findByUuid(uuid))
                .thenReturn(Optional.empty());

        final ProjectNotFoundException exception = assertThrows(
                ProjectNotFoundException.class,
                () -> projectService.get(uuid)
        );

        assertEquals("Project not found: " + uuid, exception.getMessage());
        verify(projectDao).findByUuid(uuid);
    }

    @Test
    void shouldDeactivateExistingProject() {
        final Project project = new Project("ClaudeMeter");
        final UUID uuid = project.getUuid();

        when(projectDao.findByUuid(uuid))
                .thenReturn(Optional.of(project));

        projectService.deactivate(uuid);

        assertEquals(ProjectStatus.INACTIVE, project.getStatus());

        verify(projectDao).findByUuid(uuid);
        verify(projectDao, never()).save(any(Project.class));
    }


    @Test
    void shouldActivateExistingProject() {
        final Project project = new Project("ClaudeMeter");
        final UUID uuid = project.getUuid();

        project.deactivate();

        assertEquals(ProjectStatus.INACTIVE, project.getStatus());

        when(projectDao.findByUuid(uuid))
                .thenReturn(Optional.of(project));

        projectService.activate(uuid);

        assertEquals(ProjectStatus.ACTIVE, project.getStatus());

        verify(projectDao).findByUuid(uuid);
        verify(projectDao, never()).save(any(Project.class));
    }

}
