package com.scrumSystem.project.sprintBacklog;

import com.scrumSystem.interfaces.Entity;

/**
 * Created by Matt on 11/05/2016.
 * @author Matt King
 */
public class IssueEntity implements Entity
{

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
