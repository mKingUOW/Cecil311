package com.scrumSystem.project;

import com.scrumSystem.user.UserController;

/**
 * Main class for interfacing with GUI classes
 * Created by Matt on 4/05/2016.
 * @author Matt King
 */
public class SessionController
{
    /**
     * User controller object used to interface with user classes
     */
    private UserController uc;

    /**
     * Default constructor
     */
    public SessionController()
    {
        uc = new UserController();
    }

    /**
     * GUI class calls this method when a user attempts to login to the system
     * @param username The username of the user attempting to login
     * @param password The password of the user attempting to login
     * @return Returns true if login was successful, otherwise false
     */
    public boolean attemptUserLogin(String username, String password)
    {
        return uc.attemptLogin(username, password);
    }

}
