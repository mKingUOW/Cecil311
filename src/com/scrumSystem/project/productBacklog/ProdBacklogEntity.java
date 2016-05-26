package com.scrumSystem.project.productBacklog;

import com.scrumSystem.interfaces.Entity;

/**
 * Created by Matt on 11/05/2016.
 */
public class ProdBacklogEntity implements Entity
{

    private String projectName;
    private int storyNumber;
    private String title;
    private String storyType;
    private String description;
    private String priority;
    private int effortEstimation;
    private String subType;
    private int epicRef;
    private String completeionStatus;
    private int assignedToSprint;

    public ProdBacklogEntity(){
        projectName = "null";
        storyNumber = -1;
        title = "null";
        storyType = "null";
        description = "null";
        priority = "null";
        effortEstimation = -1;
        subType = "null";
        epicRef = -1;
        completeionStatus = "incomplete";
        assignedToSprint = -1;
    }

    public String toCSV(){
        return projectName + "," + storyNumber + "," + title + "," + storyType + "," + description + "," + priority + "," + effortEstimation + "," + subType + "," + epicRef + "," + completeionStatus + "," + assignedToSprint;
    }

    public int getAssignedToSprint(){
        return assignedToSprint;
    }

    public void setAssignedToSprint(int a){
        assignedToSprint = a;
    }


    public String getProjectName(){
        return projectName;
    }

    public void setProjectName(String s){
        projectName = s;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String t){
        title = t;
    }

    public int getStoryNumber(){
        return storyNumber;
    }

    public void setStoryNumber(int s){
        storyNumber = s;
    }

    public String getStoryType(){
        return storyType;
    }

    public void setStoryType(String s){
        storyType = s;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String s){
        description = s;
    }

    public String getPriority(){
        return priority;
    }

    public void setPriority(String p){
        priority = p;
    }

    public int getEffortEstimation(){
        return  effortEstimation;
    }

    public void setEffortEstimation(int e){
        effortEstimation = e;
    }

    public String getSubType(){
        return subType;
    }

    public void setSubType(String s){
        subType = s;
    }

    public int getEpicRef(){
        return epicRef;
    }

    public void setEpicRef(int e){
        epicRef = e;
    }

    public String getCompleteionStatus(){
        return completeionStatus;
    }

    public void setCompleteionStatus(String c){
        completeionStatus = c;
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
