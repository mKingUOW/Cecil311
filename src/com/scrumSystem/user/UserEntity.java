package com.scrumSystem.user;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Entity class used to communicate with the database
 * Created by Matt on 4/05/2016.
 * @author Matt King
 */
public class UserEntity
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
     * Default constructor
     */
    public UserEntity ()
    {
        usersFile = System.getProperty("user.dir") + File.separator + "database" + File.separator + "userAccounts.csv";
        userDetailsFile = System.getProperty("user.dir") + File.separator + "database" + File.separator + "userDetails.csv";
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
        String pwdInDb;
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
                        userType = fields[2];
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
     * @param user A UserDetails object containing all the details for the account to be created
     */
    public boolean createAccount(UserDetails user)
    {
        //check the DB to see if the username already exists
        //if it already exists
        return false;
        //if the username does not exist, create the account
        //return true;

    }
}
