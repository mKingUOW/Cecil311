package com.scrumSystem.project;

import com.scrumSystem.Helpers.ProjectDetailsHelper;
import com.scrumSystem.interfaces.Entity;
import com.scrumSystem.project.productBacklog.ProdBacklogEntity;
import com.scrumSystem.project.productBacklog.ProductBacklogController;
import com.scrumSystem.project.sprintBacklog.CommentEntity;
import com.scrumSystem.project.sprintBacklog.SprintBacklogEntity;
import com.scrumSystem.user.UserController;
import com.scrumSystem.user.UserEntity;

import java.util.ArrayList;
import java.util.Date;

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
     * Project details object holding all details of the project
     */
    private ProjectDetails pd;
    private ProjectController pc;
    private ProductBacklogController pbc;
    private CommentController cc;
    private SprintController spc;
    /**
     * Default constructor
     */
    public SessionController()
    {
        uc = new UserController();
        pc = new ProjectController();
        pbc = new ProductBacklogController();
        cc = new CommentController();
        spc = new SprintController();
        pd = null;
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

    /**
     * GUI class calls this method after login to determine what UI state should be displayed
     * Three possible states can be returned
     * Project - the user that logged in has a current active project so display project UI
     * NoProject - the user that logged in has no current active project so display NoProject UI
     * Setup - This is relevant only to a Scrum Master that has logged in and will display a project setup UI
     * Error - An error occurred getting the state (invalid project or the like)
     */
    public String getStartingState()
    {
        String noProject = "noProject";
        String setup = "setup";
        String project = "project";
        String error = "error";
        String activeProject = uc.getUserActiveProject();

        if ("none".equals(activeProject))
            return noProject;

        // find the project in the database and instantiate the project
        else
        {
            //returns a project details object containing the info for the active project passed
            pd = new ProjectDetails();

            //if the project is correctly initialized
            if (pd.initProject(activeProject))
            {
                //if the user logged in is a scrum master, check if their active project needs to be setup
                if ("SM".equals(uc.getRoleType()) && pd.getCurrentSprint() == 0)
                    return setup;
                else
                    return project;
            }
            else
                return error;
        }
    }

    /**
     * GUI class calls this method to determine UI options for the user
     * @return The role type of the current user
     */
    public String getUserRoleType()
    {
        return uc.getRoleType();
    }

    public boolean createProject(String projectName, String scrumMaster)
    {
        /*
        pd = uc.createProject(projectName, scrumMaster);

        //after the project has been created by the system admin, is should be saved to the database
        if (pd != null)
        {
            pd.saveToDB();
            return true;
        }
         return false;
         */
        pc.createProject(projectName,scrumMaster);
        return true;
    }

    /**
     * GUI class calls this method when a Scrum Master logs in and is required to setup a newly created project
     * It will return the name of the project so that the GUI can display the project name at the top of the form
     * to be filled out by the scrum master
     * @return Returns the project name to be setup, which is the active project for the scrum master
     */
    public String initProjectSetup()
    {
        return uc.getUserActiveProject();
    }

    /**
     * GUI class calls this method and passes all mandatory data for initial project setup
     * @param pdh A helper object holding project data obtained from the GUI
     * @return Returns true if the project data was successfully added to the project, otherwise false
     */
    public boolean setupProject(ProjectDetailsHelper pdh)
    {
        return pd.setupProject(pdh);
    }

    /**
     * Returns an array list of all product owners within the system that are not currently assigned to a project
     * @return ArrayList of type String containing all available product owners within the system
     */
    public ArrayList<String> getAvailablePOs()
    {
        return uc.getAvailablePOs(pd);
    }

    /**
     * Returns an array list of all team members within the system that are not currently assigned to a project
     * @return ArrayList of type String containing all available product owners within the system
     */
    public ArrayList<String> getAvailableTMs()
    {
        return uc.getAvailableTMs(pd);
    }

    public ArrayList<String> getAvailableSMs(){
        return uc.getAvailableSMs(new ProjectDetails());
    }

    public UserEntity getUsersDetails(){

        return uc.getUsersDetails();

    }

    public void addUser(UserEntity u){
        uc.addUser(u);
    }

    public  ArrayList<String> getAllPorjectNames(){
        return pc.getAllPorjectNames();
    }

    public ProjectDetails getProjectDetails(String id){
       return pc.getProject(id);
    }

    public void saveProject(ProjectDetails p){
        pc.saveProject(p);
    }

    public UserEntity getUser(String id){
        return uc.getUser(id);
    }

    public void modifyUser(UserEntity p){
        uc.modifyUser(p);
    }

    public void assignPOtoProject(String proj, String uname){
        pc.addPOtoProject(proj,uname);
        //save changes to user
        UserEntity user = getUser(uname);
        user.setActiveProject(proj);
        uc.modifyUser(user);
    }

    public void clearPOsFromProject(String proj){
        //reset active project for cleared users
        ArrayList<String> users = pc.getPOsByProject(proj).getPOs();
        for(int i = 0; i<users.size(); i++){
            UserEntity temp = uc.getUser(users.get(i));
            temp.setActiveProject("none");
            uc.modifyUser(temp);
        }
        //clear from project
        pc.clearPOsFromProject(proj);

    }

    public void clearTMsFromProject(String proj){
        //reset active project for cleared users
        ArrayList<String> users = pc.getTMsByProject(proj).getTMs();
        for(int i = 0; i<users.size(); i++){
            UserEntity temp = uc.getUser(users.get(i));
            temp.setActiveProject("none");
            uc.modifyUser(temp);
        }
        //clear from project
        pc.clearTMsFromProject(proj);
    }

    public void assignTMtoProject(String proj,String uname){
        pc.addTMtoProject(proj,uname);
        //save changes to user
        UserEntity user = getUser(uname);
        user.setActiveProject(proj);
        uc.modifyUser(user);
    }

    public ProjectPOEntity getPOsByProject(String proj){
        return pc.getPOsByProject(proj);
    }

    public ProjectTMEntity getTMsByProject(String proj){
        return pc.getTMsByProject(proj);
    }

    public String getStartDate() {
        return pd.getStartDate();
    }

    public String getEndDate() {
        return pd.getEndDate();
    }

    public String getStoryPoint(){
        return pd.getStoryPointValue();
    }

    public String getScrumMaster() {
        return pd.getScrumMaster();
    }

    public int getSprintDuration(){
        return pd.getDurationOfSprint();
    }

    public String getProdOwner(){
        return pd.getProductOwner(uc.getUserActiveProject());
    }

    public int getCurrentSprint(){
        return pd.getCurrentSprint();
    }

    public ArrayList<String> getTMs(){
        return pd.getAvailableTMs();

    }

	                             /* ---------- PRODUCT BACKLOG FUNCTIONS -------------- */

    public void addBacklog(ProdBacklogEntity p){
        p.setStoryNumber(pbc.getNextStoryNum());
        pbc.addBacklog(p);
    }

    public void modifyBacklog(ProdBacklogEntity p){
        pbc.modifyBacklog(p);
    }

    public ProdBacklogEntity getBacklog(int id){
        return pbc.getBacklog(id);
    }

    public int getNewestStoryId(){
        return pbc.getNextStoryNum();
    }

    public ArrayList<Integer> getProductBacklogIDs(){
        return pbc.getProductBacklogIDs();
    }

    /* ---------- Comment FUNCTIONS -------------- */
    public void createComment(CommentEntity c){
        cc.createComment(c);
    }

    public ArrayList<CommentEntity> getCommentsByIssue(int i){
        return cc.getCommentsByIssue(i);
    }

     /* ---------- Sprint FUNCTIONS -------------- */

    public void createSprintBL(SprintBacklogEntity s){
        s.setIssueID(spc.getNextID());
        spc.addSprintBL(s);
    }

    public void modifySprintBL(SprintBacklogEntity s){
        spc.modifySprintBL(s);
    }

    public ArrayList<SprintBacklogEntity> getSprintBLsFromSprint(int id){
        return spc.getSprintBLsFromSprint(id);
    }

    public ArrayList<SprintBacklogEntity> getSprintBoardBLs(int sprintID, String username){
        return spc.getSprintBoardBLs(sprintID,username);
    }
}
