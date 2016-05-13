package com.scrumSystem.project;

import com.scrumSystem.interfaces.Entity;
import com.scrumSystem.project.productBacklog.ProdBacklogEntity;
import com.scrumSystem.project.sprintBacklog.SprintBacklogEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Class for holding project details
 * Created by Matt on 10/05/2016.
 * @author Matt King
 */
public class ProjectDetails implements Entity
{
    /**
     * Database file path for the project
     */
    private String projectFile;

    /**
     * Database file path for the product owners of the project
     */
    private String POFile;

    /**
     * Database file path for the team members of the project
     */
    private String TMFile;

    /**
     * Buffered reader object to read from files
     */
    private BufferedReader reader;

    /**
     * Print writer object to write to files
     */
    private PrintWriter writer;

    /**
     * The name of the project
     */
    private String projectName;

    /**
     * Start date for the project
     */
    private String startDate;

    /**
     * End date for the project
     */
    private String endDate;

    /**
     * Story point value in hours
     */
    private int storyPointValue;

    /**
     * Scrum Master for the project
     */
    private String scrumMaster;

    /**
     * Array list containing the product owners for the project
     */
    private ArrayList<String> productOwners;

    /**
     * Array list containing the team members for the project
     */
    private ArrayList<String> teamMembers;

    /**
     * Integer to hold the current sprint number
     */
    //zero signifies that the project has not started
    private int currentSprint;

    /**
     * Product backlog object
     */
    private ProdBacklogEntity pb;

    /**
     * Sprint backlog object
     */
    private SprintBacklogEntity sb;


    /**
     * Default constructor
     */
    public ProjectDetails()
    {
        projectFile = System.getProperty("user.dir") + File.separator + "database" + File.separator + "project.csv";
        POFile = System.getProperty("user.dir") + File.separator + "database" + File.separator + "projectPOs.csv";
        TMFile = System.getProperty("user.dir") + File.separator + "database" + File.separator + "projectTMs.csv";

        projectName = null;
    }

    /**
     * Finds project the database and initializes attributes
     * This is used when a user logs in and they have an active project
     * It is fairly complex as it needs to read in fields from the database from
     * a few different files and then call the constructors for product backlog and sprint backlog
     * which will also read from the database
     * @param projectName The name of the project to be instantiated
     * @return Returns true upon successful initialization of the project, otherwise false
     */
    public boolean initProject(String projectName)
    {
        //find and load the project from the database
        return loadFromDB(projectName);
    }

    /**
     * Constructor - for system admin use
     */
    public ProjectDetails(String projectName, String scrumMaster)
    {
        projectFile = System.getProperty("user.dir") + File.separator + "database" + File.separator + "project.csv";
        POFile = System.getProperty("user.dir") + File.separator + "database" + File.separator + "projectPOs.csv";
        TMFile = System.getProperty("user.dir") + File.separator + "database" + File.separator + "projectTMs.csv";

        this.projectName = projectName;
        this.scrumMaster = scrumMaster;
    }

    //---------------------------------------------GET/SET METHODS------------------------------------------//

    public int getCurrentSprint()
    {
        return currentSprint;
    }

    //-------------------------------------ENTITY INTERFACE OVERRIDDEN METHODS------------------------------//

    @Override
    public void saveToDB()
    {

    }

    @Override
    public boolean loadFromDB(String id)
    {
        return false;
    }
}
