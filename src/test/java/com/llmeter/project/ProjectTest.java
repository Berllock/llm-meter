package com.llmeter.project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    //Verify creation for a new project
    @Test
    void shouldCreateActiveProject() {

        Project p = new Project("ClaudeMeter");

        assertNotNull(p.getUuid);
        asssertEquals("ClaudeMeter", p.getName());
        asssertEquals(ProjectStatus.ACTIVE, p.getStatus());
        assertNotNull(p.getCreatedAt());
    
        //Since the entity has not been persisted yet, it has no database ID.
        assertNull(p.getId());
    }

    //Test the deactivate behavior
    @Test shouldDeactivateProject() {
        Project p = new Project("ClaudeMeter");

        p.deactivate();
        asssertEquals(ProjectStatus.INACTIVE, p.getStatus());
    }

    

}