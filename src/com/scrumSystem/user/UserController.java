package com.scrumSystem.user;

import com.scrumSystem.project.ProjectDetails;
import com.scrumSystem.role.*;
import com.scrumSystem.role.RoleFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

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
     * Role object used by the user
     */
    private Role role;

    /**
     * Role type of the user who is logged in
     */
    private String userRoleType;

    private String usersFile;
    private ArrayList<UserEntity> users;

    /**
     * Default constructor
     */
    public UserController()
    {

        user = new UserEntity();
        usersFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "database" + File.separator + "userAccounts.csv";
        users = getAllUsers();
    }

    /**
     * Session controller calls this method when a user attempts to login into the system
     * @param username The username of the user attempting to login
     * @param password The password of the user attempting to login
     * @return The string returned by the UserEntity login method
     */
    public boolean attemptLogin(String username, String password)
    {
        userRoleType = user.login(username, password);

        //if login failed then we return false to GUI class (boundary class)
        if ("Failure".equals(userRoleType))
            return false;

        //pass the role type string to the role factory to create the role
        role = RoleFactory.createRole(userRoleType);
        user.loadFromDB(username);
        return true;
    }

    /**
     * Gets the role type of the user who is currently logged in
     * @return The role type of the current user
     */
    public String getRoleType()
    {
        return userRoleType;
    }

    /**
     * System admin exclusive method for creating a new project
     * If the role object is not an instance of SystemAdmin, it will return null
     * @param projectName The name of the project to be created
     * @param scrumMaster The username of the scrum master assigned to the project
     * @return The new project details object
     */
    public ProjectDetails createProject(String projectName, String scrumMaster)
    {
        //ensures that role is an instance of System Admin object then casts to the correct type
        if (role instanceof SystemAdmin)
            return ((SystemAdmin)role).createProject(projectName, scrumMaster);
        return null;
    }

    /**
     * Calls the user entity's getActiveProject method which returns the active project of the user
     * @return A String containing the active project of the user
     */
    public String getUserActiveProject()
    {
        return user.getActiveProject();
    }

    /**
     * Returns an array list of all product owners within the system that are not currently assigned to a project
     * @return ArrayList of type String containing all available product owners within the system
     */
    public ArrayList<String> getAvailablePOs(ProjectDetails pd)
    {
        //only scrum master and system admin will ever need this functionality
        if (role instanceof ScrumMaster || role instanceof SystemAdmin)
            return pd.getAvailablePOs();
        return null;
    }

    /**
     * Returns an array list of all team members within the system that are not currently assigned to a project
     * @return ArrayList of type String containing all available product owners within the system
     */
    public ArrayList<String> getAvailableTMs(ProjectDetails pd)
    {
        //only scrum master and system admin will ever need this functionality
        if (role instanceof ScrumMaster || role instanceof SystemAdmin)
            return pd.getAvailableTMs();
        return null;
    }

    public ArrayList<String> getAvailableSMs(ProjectDetails pd)
    {
        //only scrum master and system admin will ever need this functionality
        if (role instanceof ScrumMaster || role instanceof SystemAdmin){
            if(pd == null){
                System.out.println("pd null");
            }
            else{
                return pd.getAvailableSMs();
            }

        }


        return null;
    }

    public UserEntity getUsersDetails(){
        return user;
    }

    public ArrayList<UserEntity> getAllUsers(){
        ArrayList<UserEntity> allUsers = new ArrayList<UserEntity>();
        String lineInFile;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(usersFile));
            lineInFile = reader.readLine();

            while (lineInFile != null) {
                String [] fields = lineInFile.split(",");
                UserEntity temp = new UserEntity();
                //System.out.println(fields[0]);
                temp.setUsername(fields[0]);
                temp.setPassword(fields[1]);
                temp.setUserType(fields[2]);
                temp.setActiveProject(fields[3]);
                allUsers.add(temp);
                //read the next line in the file
                lineInFile = reader.readLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(allUsers.size() + " users in system");
        return allUsers;
    }

    public void assignUserToProject(String username,String projName){
        users.clear();
        users = getAllUsers();
        for(int i = 0; i<users.size(); i++){
            System.out.println(i + ": " + users.get(i).getUsername());
            if(users.get(i).getUsername().equals(username)){
                System.out.println("found " + username);
                UserEntity temp = users.get(i);
                temp.setActiveProject(projName);
            }
        }
        saveUsers();
    }

    public void saveUsers(){
        try{
            PrintWriter writer = new PrintWriter(usersFile);
            for(int i = 0; i<users.size(); i++){
                writer.println(users.get(i).accountToCSV());
            }
            writer.close();
        }catch(Exception err){
            System.out.println(err);
        }

    }


}
