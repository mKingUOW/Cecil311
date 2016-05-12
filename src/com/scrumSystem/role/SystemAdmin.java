package com.scrumSystem.role;

import com.scrumSystem.project.ProjectDetails;
import com.scrumSystem.user.UserEntity;

/**
 * System admin class which extends the Role class
 * Created by Matt on 4/05/2016.
 */
public class SystemAdmin extends Role
{
    /**
     * Default Constructor
     */
    public SystemAdmin() {}

    /**
     * Creates a new project details object to be populated with data from the GUI
     * @param projectName Name of the project to be created
     * @param scrumMaster Username of the assigned scrum master for the project
     * @return returns a new project details object to be populated
     */
    public ProjectDetails createProject(String projectName, String scrumMaster)
    {
        return new ProjectDetails(projectName, scrumMaster);
    }

    /**
     * Creates a new user within the system using the data from the GUI
     * @param username The username of the user
     * @param password The password of the user
     * @param firstName The first name of the user
     * @param lastName The last name of the user
     * @param email The email address of the user
     * @param skills The skills the user possesses
     * @return Returns a user details object containing all data passed in
     */
    public UserEntity createUser(String username, String password, String firstName, String lastName, String email, String skills, String roleType)
    {
        return new UserEntity(username, password, firstName, lastName, email, skills, roleType);
    }
}
