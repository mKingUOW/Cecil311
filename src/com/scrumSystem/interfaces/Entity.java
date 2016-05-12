package com.scrumSystem.interfaces;

/**
 * Interface that all entity classes should implement
 * Created by Matt on 11/05/2016.
 * @author Matt King
 */
public interface Entity
{
    void saveToDB();
    boolean loadFromDB(String id);
}
