package com.scrumSystem.project.sprintBacklog;

import com.scrumSystem.interfaces.Entity;

/**
 * Created by Matt on 11/05/2016.
 * @author Matt King
 */
public class CommentEntity implements Entity
{

    private String projectName;
    private int issueNumber;
    private String comment;
    private String username;
    private String date;

    public CommentEntity(){
        projectName = "null";
        issueNumber = -1;
        comment = "null";
        username = "null";
        date = null;
    }

    public String getProjectName(){
        return projectName;
    }

    public void setProjectName(String p){
        projectName = p;
    }

    public int getIssueNumber(){
        return issueNumber;
    }

    public void setIssueNumber(int i){
        issueNumber = i;
    }

    public String getComment(){
        return comment;
    }

    public void setComment(String c){
        comment = c;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String u){
        username = u;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String d){
        date = d;
    }

    public String toCSV(){
        return projectName + "," + issueNumber + "," + comment  + "," + username  + "," + date;
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
