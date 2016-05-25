package com.scrumSystem.user;

import com.scrumSystem.interfaces.Entity;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Entity class used to communicate with the database regarding information relating to users
 * Created by Matt on 4/05/2016.
 * @author Matt King
 */
public class UserEntity implements Entity
{
    /**
     * Reference to database file containing all user login details
     */
    private String usersFile;

    /**
     * Reference to database file containing all user details
     */
    private String userDetailsFile;

    /**
     * Buffered reader object for reading from files
     */
    private BufferedReader reader;

    /**
     * PrintWriter object for writing to files
     */
    private PrintWriter writer;

    /**
     * Holds the users current active project
     */
    private String activeProject;

    /**
     * Username associated with the details
     */
    private String username;

    /**
     * Password of the user
     */
    private String password;

    /**
     * First name of the user
     */
    private String firstName;

    /**
     * Last Name of the user
     */
    private String lastName;

    /**
     * Email address of the user
     */
    private String email;

    /**
     * Skills the user has. Mainly used for dev team.
     */
    private String skills;

    /**
     * Usertype of the user.
     */
    private String userType;


    /**
     * Default constructor
     */
    public UserEntity ()
    {
        usersFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "database" + File.separator + "userAccounts.csv";
        userDetailsFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "database" + File.separator + "userDetails.csv";
    }

    /**
     * Constructor
     * Accepts user details as parameters
     * @param username The username of the user
     * @param password The password of the user
     * @param firstName The first name of the user
     * @param lastName The last name of the user
     * @param email The email address of the user
     * @param skills The skills the user possesses
     */
    public UserEntity(String username, String password, String firstName, String lastName, String email, String skills, String roleType)
    {
        usersFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "database" + File.separator + "userAccounts.csv";
        userDetailsFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "database" + File.separator + "userDetails.csv";

        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.skills = skills;
        this.userType = roleType;
    }

    /**
     * Returns the username of the user
     * @return A string holding the username
     */
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String u){
        username = u;
    }

    /**
     * Returns the password of the user
     * @return A string holding the password
     */
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String pwd){
        password = pwd;
    }

    /**
     * Returns the first name of the user
     * @return A string holding the first name
     */
    public String getFirstName()
    {
        return firstName;
    }

    public void setFName(String f){
        firstName = f;
    }

    /**
     * Returns the last name of the user
     * @return A string holding the last name
     */
    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String l){
        lastName = l;
    }

    /**
     * Returns the email address of the user
     * @return A string holding the email address
     */
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String e){
        email = e;
    }

    /**
     * Returns the skills the user possesses
     * @return A string holding the skills
     */
    public String getSkills()
    {
        return skills;
    }

    public void setSkills(String s){
        skills = s;
    }

    /**
     * Returns the active project for the user
     * @return The current active project name
     */
    public String getActiveProject() { return activeProject; }

    public void setActiveProject(String a){
        activeProject = a;
    }

    /**
     * Returns the user type of the user
     * @return A string holding the user type
     */
    public String getUserType() {return userType;};

    public void setUserType(String u){
        userType = u;
    }

    /**
     * Login method for system access
     * @param username The username of the user who is attempting to login
     * @param password The password of the user who is attempting to login
     * @return Returns a String describing the user type that has successfully logged in. Returns "Failure" if login fails
     */
    public String login(String username, String password)
    {
        String userType = "Failure";
        String lineInFile;
        boolean found = false;

        try
        {
            reader = new BufferedReader(new FileReader(usersFile));
            lineInFile = reader.readLine();

            //find the line where the username resides
            while (!found && lineInFile != null)
            {
                //noinspection Since15
                String[] fields = lineInFile.split(",");

                if (username.equals(fields[0]))
                {
                    found = true;
                    //check the password on that line with the password passed in
                    if (password.equals(fields[1]))
                    {
                        userType = fields[2];
                        activeProject = fields[3];
                    }
                }
                //read the next line in the file
                lineInFile = reader.readLine();
            }
        }
        catch (Exception e) {e.printStackTrace();}

        return userType;
    }

    /**
     * Account creation method - ONLY SYSTEM ADMIN CAN CREATE NEW ACCOUNTS
     * @param user A UserEntity object containing all the details for the account to be created
     */
    public boolean createAccount(UserEntity user)
    {
        //check the DB to see if the username already exists
        //if it already exists
        return false;
        //if the username does not exist, create the account
        //return true;

    }

    public String accountToCSV(){
        return username + "," + password + "," + userType + "," + activeProject;
    }

    public String detailsToCSV(){
        return username + "," + firstName + "," + lastName + "," + email + "," + skills;
    }

    @Override
    public void saveToDB()
    {

    }

    @Override
    public boolean loadFromDB(String id)
    {
        String lineInFile;
        try {
            reader = new BufferedReader(new FileReader(userDetailsFile));
            lineInFile = reader.readLine();

            while (lineInFile != null){
                String [] fields = lineInFile.split(",");
                if (id.equals(fields[0])){
                    username = fields[0];
                    firstName = fields[1];
                    lastName = fields[2];
                    email = fields[3];
                    skills = fields[4];
                    reader.close();
                    getUserAccount(id);
                    return true;
                }
                //read the next line in the file
                lineInFile = reader.readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean getUserAccount(String id){
        String lineInFile;
        try {
            reader = new BufferedReader(new FileReader(usersFile));
            lineInFile = reader.readLine();

            while (lineInFile != null){
                String [] fields = lineInFile.split(",");
                if (id.equals(fields[0])){
                    password = fields[1];
                    userType = fields[2];
                    activeProject = fields[3];
                    reader.close();
                    return true;
                }
                //read the next line in the file
                lineInFile = reader.readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean getUserDetails(String id){
        String lineInFile;
        try {
            reader = new BufferedReader(new FileReader(userDetailsFile));
            lineInFile = reader.readLine();

            while (lineInFile != null){
                String [] fields = lineInFile.split(",");
                if (id.equals(fields[0])){
                    username = fields[0];
                    firstName = fields[1];
                    lastName = fields[2];
                    email = fields[3];
                    skills = fields[4];
                    reader.close();
                    return true;
                }
                //read the next line in the file
                lineInFile = reader.readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
