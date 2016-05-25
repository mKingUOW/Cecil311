package com.scrumSystem.GUI;

import com.scrumSystem.project.ProjectDetails;
import com.scrumSystem.project.SessionController;
import com.scrumSystem.user.UserEntity;

import javax.swing.*;
import java.util.ArrayList;


/**
 * Created by Darryl on 12/05/2016.
 */
public class MemberView extends JPanel {

    private JPanel currentView;
    private String username;
    private UserEntity ue;
    public SessionController sc;


    public MemberView(){

        currentView = this;
        username = "";

    }

    public void setCurrentView(JPanel next){
        currentView = next;
    }
     public JPanel getCurrentView(){
         return currentView;
     }

    public void setSessionController(SessionController s){
        sc = s;
    }

    public void deselectAllNavigatorButtons(){
        //overriden by sub-class
    }

    public void setUserEntity(UserEntity u){
        ue = u;
    }

    public void setUsername(String u){
        username = u;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword()
    {
       return ue.getPassword();
    }

    public String getFName(){
        return ue.getFirstName();
    }

    public String getLName(){
        return ue.getLastName();
    }

    public String getEmail(){
        return ue.getEmail();
    }

    public String getSkills(){
        return ue.getSkills();
    }

    public String getUserType(){
        return ue.getUserType();
    }

    public String getActiveProj(){
        return ue.getActiveProject();
    }

    public String getStartingDate(){
        return sc.getStartDate();
    }

    public String getEndDate() {
        return sc.getEndDate();
    }

    public String getStoryPoint() {
        return sc.getStoryPoint();
    }

    public String getScrumMaster(){
        return sc.getScrumMaster();
    }

    public int getSprintDuration(){
        return sc.getSprintDuration();
    }

    public String getProdOwner(){
        return sc.getProdOwner();
    }

    public ArrayList<String> getTM(){
        return sc.getTMs();
    }
}
