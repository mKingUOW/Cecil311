package com.scrumSystem.project.sprintBacklog;

import com.scrumSystem.interfaces.Entity;

/**
 * Created by Matt on 11/05/2016.
 * @author Matt King
 */
public class SprintBacklogEntity implements Entity
{
    private String projectName;
    private int sprintID;
    private int issueID;
    private String description;
    private String issueType;
    private String priority;
    private int storyLink;
    private int storyPoints;
    private String completionStatus;
    private String assignedUser;
    private String dateStarted;
    private String dateEnded;

    public SprintBacklogEntity(){
        projectName = "null";
        sprintID = -1;
        issueID = -1;
        description  = "null";
        issueType = "null";
        priority = "null";
        storyLink = -1;
        storyPoints = -1;
        completionStatus  = "ToDo";
        assignedUser = "none";
        dateStarted = "null";
        dateEnded = "null";
    }


    public String toCSV(){
        return projectName + "," + sprintID  + "," + issueID  + "," + description  + "," + issueType  + "," + priority  + "," + storyLink  + "," + storyPoints
                + "," + completionStatus  + "," + assignedUser + "," + dateStarted  + "," + dateEnded;
    }


    public String getProjectName(){
        return projectName;
    }

    public void setProjectName(String n){
        projectName = n;
    }

    public int getSprintID(){
        return sprintID;
    }

    public void setSprintID(int i){
        sprintID = i;
    }

    public int getIssueID(){
        return issueID;
    }

    public void setIssueID(int i){
        issueID = i;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String s){
        description = s;
    }

    public String getIssueType(){
        return issueType;
    }

    public void setIssueType(String i){
        issueType = i;
    }

    public String getPriority(){
        return priority;
    }

    public void setPriority(String p){
        priority = p;
    }

    public int getStoryLink(){
        return storyLink;
    }

    public void setStoryLink(int s){
        storyLink = s;
    }

    public int getStoryPoints(){
        return storyPoints;
    }

    public void setStoryPoints(int s){
        storyPoints = s;
    }

    public String getCompletionStatus(){
        return completionStatus;
    }

    public void setCompletionStatus(String s){
        completionStatus = s;
    }

    public String getAssignedUser(){
        return assignedUser;
    }

    public void setAssignedUser(String u){
        assignedUser = u;
    }

    public String getDateStarted(){
        return dateStarted;
    }

    public void setDateStarted(String d){
        dateStarted = d;
    }

    public String getDateEnded(){
        return dateEnded;
    }

    public void setDateEnded(String d){
        dateEnded = d;
    }

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
