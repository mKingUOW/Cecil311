package com.scrumSystem.project.productBacklog;

import com.scrumSystem.interfaces.Entity;

/**
 * Created by Matt on 11/05/2016.
 */
public class ProdBacklogEntity implements Entity
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
