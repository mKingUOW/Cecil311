package com.scrumSystem.role;

import com.scrumSystem.project.ProjectDetails;
import com.scrumSystem.project.productBacklog.ProdBacklogEntity;

/**
 * Scrum master class which extends the Role class
 * Created by Matt on 4/05/2016.
 */
public class ScrumMaster extends Role
{
    /**
     * This method is used when a project has been created by the system admin but contains skeletal data
     * @return Returns a project details object
     */
    public ProjectDetails setupProject()
    {
        return new ProjectDetails();
    }
}
