package com.scrumSystem.GUI;

import javax.swing.*;

/**
 * Created by Darryl on 12/05/2016.
 */
public class MemberView extends JPanel {

    private JPanel currentView;
    private String username;

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

    public void deselectAllNavigatorButtons(){
        //overriden by sub-class
    }

    public void setUsername(String u){
        username = u;
    }

    public String getUsername(){
        return username;
    }
}
