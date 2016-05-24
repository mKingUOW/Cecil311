package com.scrumSystem.Helpers;

import java.util.ArrayList;

/**
 * GUI classes should use this object when passing data to control classes
 * Primarily used for passing data around e.g. GUI classes to control classes
 * Created by Matt on 12/05/2016.
 * @author Matt King
 */
public class ProjectDetailsHelper
{
    private String projectName;

    private String startDate;

    private String endDate;

    private String storyPointValue;

    private String scrumMaster;

    private int currentSprint;

    private int durationOfSprint;

    private ArrayList<String> POs;

    private ArrayList<String> TMs;

    //may need to add product backlog and sprint backlog objects if needed

    public ProjectDetailsHelper()
    {
        projectName = "null";
        startDate = "null";
        endDate = "null";
        storyPointValue = "null";
        scrumMaster = "null";
        currentSprint = 0;
        durationOfSprint = 0;

        POs = new ArrayList<String>();
        TMs = new ArrayList<String>();
    }

    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    public void setStoryPointValue(String storyPointValue)
    {
        this.storyPointValue = storyPointValue;
    }

    public void setScrumMaster(String scrumMaster)
    {
        this.scrumMaster = scrumMaster;
    }

    public void setCurrentSprint(int currentSprint)
    {
        this.currentSprint = currentSprint;
    }

    public void setDurationOfSprint(int durationOfSprint)
    {
        this.durationOfSprint = durationOfSprint;
    }

    public void addPOwner(String PO)
    {
        POs.add(PO);
    }

    public void addTMember(String TM)
    {
        TMs.add(TM);
    }

    public String getProjectName()
    {
        return projectName;
    }

    public String getStartDate()
    {
        return startDate;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public String getStoryPointValue()
    {
        return storyPointValue;
    }

    public String getScrumMaster()
    {
        return scrumMaster;
    }

    public int getCurrentSprint()
    {
        return currentSprint;
    }

    public int getDurationOfSprint()
    {
        return durationOfSprint;
    }

    public ArrayList<String> getPOs()
    {
        return POs;
    }

    public ArrayList<String> getTMs()
    {
        return TMs;
    }
}
