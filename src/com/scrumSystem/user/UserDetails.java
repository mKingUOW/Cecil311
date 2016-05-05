package com.scrumSystem.user;

/**
 * Holds information pertaining to a particular user within the system
 * Main use is for displaying, editing and creating user information
 * Created by Matt on 4/05/2016.
 * @author Matt King
 */
public class UserDetails
{
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
     * Default constructor
     */
    public UserDetails() {}

    /**
     * Constructor
     * Accepts all user details as parameters
     * @param username The username of the user
     * @param password The password of the user
     * @param firstName The first name of the user
     * @param lastName The last name of the user
     * @param email The email address of the user
     * @param skills The skills the user possesses
     */
    public UserDetails(String username, String password, String firstName, String lastName, String email, String skills)
    {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.skills = skills;
    }

    /**
     * Returns the username of the user
     * @return A string holding the username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Returns the password of the user
     * @return A string holding the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Returns the first name of the user
     * @return A string holding the first name
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Returns the last name of the user
     * @return A string holding the last name
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * Returns the email address of the user
     * @return A string holding the email address
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Returns the skills the user possesses
     * @return A string holding the skills
     */
    public String getSkills()
    {
        return skills;
    }
}
