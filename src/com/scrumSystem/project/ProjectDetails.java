package com.scrumSystem.project;

import com.scrumSystem.Helpers.ProjectDetailsHelper;
import com.scrumSystem.interfaces.Entity;
import com.scrumSystem.project.productBacklog.ProdBacklogEntity;
import com.scrumSystem.project.sprintBacklog.SprintBacklogEntity;

import java.io.*;
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
     *  Database file path for the user accounts
     *  Used to return users not assigned to a active project
     */
    private String UAFile;

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
    private String storyPointValue;
    //private int storyPointValue;

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

    private ArrayList<String> scrumMasters;

    /**
     * Integer to hold the current sprint number
     */
    //zero signifies that the project has not started
    private int currentSprint;

    /**
     * Integer to hold the duration of the sprint
     */
    private int durationOfSprint;

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
        projectFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "database" + File.separator + "project.csv";
        POFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "database" + File.separator + "projectPOs.csv";
        TMFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "database" + File.separator + "projectTMs.csv";
        UAFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "database" + File.separator + "userAccounts.csv";

        projectName = null;
    }

    /**
     * Constructor - for system admin use
     */
    public ProjectDetails(String projectName, String scrumMaster)
    {
        projectFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "database" + File.separator + "project.csv";
        POFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "database" + File.separator + "projectPOs.csv";
        TMFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "database" + File.separator + "projectTMs.csv";
        UAFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "database" + File.separator + "userAccounts.csv";

        this.projectName = projectName;
        this.scrumMaster = scrumMaster;
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
     * Sets a new projects attributes. New project means a project that has just been setup by the scrum master of the project
     * @param pdh A helper object holding project data obtained from the GUI
     * @return Returns true if the project is successfully setup, otherwise false
     */
    public boolean setupProject(ProjectDetailsHelper pdh)
    {
        try {
            this.projectName = pdh.getProjectName();
            this.startDate = pdh.getStartDate();
            this.endDate = pdh.getEndDate();
            this.storyPointValue = pdh.getStoryPointValue();
            this.scrumMaster = pdh.getScrumMaster();
            this.currentSprint = pdh.getCurrentSprint();
            this.durationOfSprint = pdh.getDurationOfSprint();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //---------------------------------------------GET/SET METHODS------------------------------------------//

    public String getName(){
        return projectName;
    }

    public String getStartDate(){
        return startDate;
    }

    public String getEndDate(){
        return endDate;
    }

    public String getStoryPointValue(){
        return storyPointValue;
    }

    public String getScrumMaster(){
        return scrumMaster;
    }

    public int getDurationOfSprint(){
        return durationOfSprint;
    }

    public int getCurrentSprint()
    {
        return currentSprint;
    }

    /**
     * Returns a list of product owner usernames available for project assignment
     * @return Array list of type String containing all product owner usernames available for project assignment
     */
    public ArrayList<String> getAvailablePOs()  {
        productOwners = new ArrayList<>();
        String lineInFile;
        String none = "none";
        String userType = "PO";
        try {
            reader = new BufferedReader(new FileReader(UAFile));
            lineInFile = reader.readLine();

            while (lineInFile != null) {
                String [] fields = lineInFile.split(",");
                if (userType.equals(fields[2]) && none.equals(fields[3])) {
                    productOwners.add(fields[0]);
                }
                //read the next line in the file
                lineInFile = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productOwners;
    }

    /**
     * Returns a list of team member usernames available for project assignment
     * @return Array list of type String containing all team members usernames available for project assignment
     */
    public ArrayList<String> getAvailableTMs() {
        teamMembers = new ArrayList<>();
        String lineInFile;
        String none = "none";
        String userType = "TM";

        try {
            reader = new BufferedReader(new FileReader(UAFile));
            lineInFile = reader.readLine();

            while (lineInFile != null) {
                String [] fields = lineInFile.split(",");
                if (userType.equals(fields[2]) && none.equals(fields[3])) {
                    teamMembers.add(fields[0]);
                }
                //read the next line in the file
                lineInFile = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teamMembers;
    }

    public ArrayList<String> getAvailableSMs() {
        System.out.println("in here");
        scrumMasters = new ArrayList<>();
        String lineInFile;
        String none = "none";
        String userType = "SM";

        try {
            reader = new BufferedReader(new FileReader(UAFile));
            lineInFile = reader.readLine();

            while (lineInFile != null) {
                String [] fields = lineInFile.split(",");
                if (userType.equals(fields[2]) && none.equals(fields[3])) {
                    scrumMasters.add(fields[0]);
                }
                //read the next line in the file
                lineInFile = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scrumMasters;
    }



    //-------------------------------------ENTITY INTERFACE OVERRIDDEN METHODS------------------------------//

    @Override
    public void saveToDB() {

        try {
            writer = new PrintWriter(projectFile);
            writer.append(projectName + "," + startDate + "," + endDate + "," + storyPointValue + "," + scrumMaster
                            + "," + currentSprint + "," + durationOfSprint);

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public boolean loadFromDB(String id)
    {

        String lineInFile;

        try {
            reader = new BufferedReader(new FileReader(projectFile));
            lineInFile = reader.readLine();

            while (lineInFile != null) {
                String [] fields = lineInFile.split(",");
                if (id.equals(fields[0])) {
                    projectName = fields[0];
                    startDate = fields[1];
                    endDate = fields[2];
                    storyPointValue = fields[3];
                    scrumMaster = fields[4];
                    currentSprint = Integer.parseInt(fields[5]);
                    durationOfSprint = Integer.parseInt(fields[6]);

                    reader.close();
                    return true;
                }
                //read the next line in the file
                lineInFile = reader.readLine();
            }
        } catch (Exception e) {e.printStackTrace();}

        return false;
    }
}
