package com.llmeter.project;

import com.llmeter.project.domain.Project;
import com.llmeter.project.domain.ProjectStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    //Verify creation for a new project
    @Test
    void shouldCreateActiveProject() {

        Project p = new Project("ClaudeMeter");

        assertNotNull(p.getUuid());
        assertEquals("ClaudeMeter", p.getName());
        assertEquals(ProjectStatus.ACTIVE, p.getStatus());
        assertNotNull(p.getCreatedAt());
    
        //Since the entity has not been persisted yet, it has no database ID.
        assertNull(p.getId());
    }

    //Test the deactivate behavior
    @Test 
    void shouldDeactivateProject() {
        Project p = new Project("ClaudeMeter");

        p.deactivate();
        assertEquals(ProjectStatus.INACTIVE, p.getStatus());
    }

    

}
