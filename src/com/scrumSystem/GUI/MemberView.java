package com.scrumSystem.GUI;

import javax.swing.*;

/**
 * Created by Darryl on 12/05/2016.
 */
public class MemberView extends JPanel {

    private JPanel currentView;

    public MemberView(){
        currentView = this;
    }

    public void setCurrentView(JPanel next){
        currentView = next;
    }

    public void deselectAllNavigatorButtons(){
        //overriden by sub-class
    }
}
