package com.scrumSystem.user;

/**
 * Controller class for user related functionality
 * Created by Matt on 4/05/2016.
 * @author Matt King
 */
public class UserController
{
    /**
     * User entity object used to query database
     */
    private UserEntity user;

    /**
     * User details object to hold the details of the user
     */
    private UserDetails details;

    /**
     * Role object used by the user
     */
    //private Role role;

    /**
     * Role factory object to create the role for the logged in user
     */
    //private RoleFactory rf;

    /**
     * Default constructor
     */
    public UserController()
    {
        user = new UserEntity();
    }

    /**
     * Session controller calls this method when a user attempts to login into the system
     * @param username The username of the user attempting to login
     * @param password The password of the user attempting to login
     * @return The string returned by the UserEntity login method
     */
    public boolean attemptLogin(String username, String password)
    {
        String roleType = user.login(username, password);

        //if login failed then we return false to GUI class (boundary class)
        if ("Failure".equals(roleType))
            return false;

        //pass the role type string to the role factory to create the role
        //role = rf.createRole(roleType);
        return true;
    }
}
