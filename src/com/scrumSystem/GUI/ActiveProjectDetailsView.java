package com.scrumSystem.GUI;

import com.scrumSystem.project.ProjectDetails;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Darryl on 22-May-16.
 */
public class ActiveProjectDetailsView extends JPanel{

    private JFrame parentFrame;
    private MemberView parentPanel;



    private JPanel projNamePanel;
    private JPanel startDatePanel;
    private JPanel endDatePanel;
    private JPanel pointPanel;
    private JPanel smPanel;
    private JPanel durPanel;
    private JPanel currSprintPanel;
    private JPanel buttonPanel;
    private JPanel poSelectPanel;
    private JPanel tmAssignPanel;



    private JLabel projNameField;
    private JLabel startDateField;
    private JLabel endDateField;
    private JLabel pointField;
    private JLabel durField;
    private JLabel currSprintField;

    private TeamMembersScrollPanel teamMembersScrollPanel;

    public ActiveProjectDetailsView(JFrame p, MemberView pp){
        parentFrame = p;
        parentPanel = pp;

        prepare();
    }

    public void prepare(){
        setLayout(new BorderLayout());

        JPanel centerLayout = new JPanel();
        centerLayout.setLayout(new GridLayout(1,2));


        //header panel
        JLabel header = new JLabel("Active Project",SwingConstants.CENTER);
        header.setPreferredSize(new Dimension(1000,100));
        add(header,BorderLayout.NORTH);

        //center panel
        JPanel centerLeftLayout = new JPanel();
        centerLeftLayout.setLayout(new GridLayout(8,1));


        //proj name panel
        projNamePanel = new JPanel();
        projNamePanel.setLayout(new GridBagLayout());
        JLabel projNameLabel = new JLabel("Project Name: ");
        projNameField = new JLabel(parentPanel.getActiveProj());
        projNamePanel.add(projNameLabel);
        projNamePanel.add(projNameField);
        centerLeftLayout.add(projNamePanel);

        //start date panel
        startDatePanel = new JPanel();
        startDatePanel.setLayout(new GridBagLayout());
        JLabel startDateLabel = new JLabel("Start Date: ");
        startDateField = new JLabel(parentPanel.getStartingDate());
        startDatePanel.add(startDateLabel);
        startDatePanel.add(startDateField);
        centerLeftLayout.add(startDatePanel);

        //end date panel
        endDatePanel = new JPanel();
        endDatePanel.setLayout(new GridBagLayout());
        JLabel endDateLabel = new JLabel("End Date: ");
        endDateField = new JLabel(parentPanel.getEndDate());
        endDatePanel.add(endDateLabel);
        endDatePanel.add(endDateField);
        centerLeftLayout.add(endDatePanel);

        //story point panel
        pointPanel = new JPanel();
        pointPanel.setLayout(new GridBagLayout());
        JLabel pointLabel = new JLabel("Story Point Value: ");
        pointField = new JLabel(parentPanel.getStoryPoint());
        pointPanel.add(pointLabel);
        pointPanel.add(pointField);
        centerLeftLayout.add(pointPanel);

        //scrum master panel
        smPanel = new JPanel();
        smPanel.setLayout(new GridBagLayout());
        JLabel smLabel = new JLabel("Scrum Master: ");
        JLabel smField = new JLabel(parentPanel.getScrumMaster());
        smPanel.add(smLabel);
        smPanel.add(smField);
        centerLeftLayout.add(smPanel);

        //sprint duration panel
        durPanel = new JPanel();
        durPanel.setLayout(new GridBagLayout());
        JLabel durLabel = new JLabel("Sprint Duration (weeks): ");
        durField = new JLabel(String.valueOf(parentPanel.getSprintDuration()));
        durPanel.add(durLabel);
        durPanel.add(durField);
        centerLeftLayout.add(durPanel);

        //current Sprint panel
        currSprintPanel = new JPanel();
        currSprintPanel.setLayout(new GridBagLayout());
        JLabel currSprintLabel = new JLabel("Current Sprint: ");
        currSprintField = new JLabel(Integer.toString(parentPanel.sc.getCurrentSprint()));
        currSprintPanel.add(currSprintLabel);
        currSprintPanel.add(currSprintField);
        centerLeftLayout.add(currSprintPanel);

        //center right layout
        JPanel centerRightLayout = new JPanel();
        centerRightLayout.setLayout(new BorderLayout());

        //product owner selector panel
        poSelectPanel = new JPanel();
        poSelectPanel.setLayout(new GridBagLayout());
        JLabel poSelectLabel = new JLabel("Product Owner: ");
        JLabel poField = new JLabel(parentPanel.getProdOwner());
        poSelectPanel.add(poSelectLabel);
        poSelectPanel.add(poField);
        centerRightLayout.add(poSelectPanel,BorderLayout.NORTH);

        //team member assign panel
        tmAssignPanel = new JPanel();
        tmAssignPanel.setLayout(new BorderLayout());
        JLabel tmHeader = new JLabel("Assigned Team members: ");
        tmAssignPanel.add(tmHeader,BorderLayout.NORTH);


        teamMembersScrollPanel = new TeamMembersScrollPanel(parentFrame,parentPanel,this);
        teamMembersScrollPanel.addElement(parentPanel.getUsername());
        BacklogScrollPane backlogScrollPane = new BacklogScrollPane(1000,1000);
        backlogScrollPane.setScrollPanel(teamMembersScrollPanel);
        tmAssignPanel.add(backlogScrollPane,BorderLayout.CENTER);


        centerRightLayout.add(tmAssignPanel,BorderLayout.CENTER);

        centerLayout.add(centerLeftLayout);
        centerLayout.add(centerRightLayout);
        add(centerLayout,BorderLayout.CENTER);

    }


}
