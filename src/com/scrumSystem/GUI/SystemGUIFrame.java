package com.scrumSystem.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Main class for the system GUI
 * Created by Matt on 4/05/2016.
 * @author Matt King
 */
public class SystemGUIFrame extends JFrame
{
    //define system frame GUI
    //frame = new JFrame("Team Member View");
    //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    public SystemGUIFrame(){
        //setSize(new Dimension(1500, 720));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LoginView loginView = new LoginView(this);
        loginView.showView();


       // TeamMemberView teamMemberView = new TeamMemberView(this);
        //teamMemberView.showView();

        //ProductOwnerView productOwnerView = new ProductOwnerView(this);
        //productOwnerView.showView();

    }

}