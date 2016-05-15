package com.scrumSystem.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Darryl on 11/05/2016.
 */
public class SprintBoardView extends JPanel{

    private JFrame parentFrame;
    private JPanel returnView;

    private SprintBacklogScrollPanel todoScrollPanel;
    private SprintBacklogScrollPanel inProgressScrollPanel;
    private SprintBacklogScrollPanel reviewScrollPanel;
    private SprintBacklogScrollPanel testingScrollPanel;
    private SprintBacklogScrollPanel completeScrollPanel;

    private BacklogScrollPane todoBacklogScrollPane;
    private BacklogScrollPane inProgressBacklogScrollPane;
    private BacklogScrollPane reviewBacklogScrollPane;
    private BacklogScrollPane testingBacklogScrollPane;
    private BacklogScrollPane completeBacklogScrollPane;

    public SprintBoardView(JFrame p, JPanel ret){
        parentFrame = p;
        returnView = ret;
        prepare();
    }

    public void prepare(){

        JScrollPane outerScrollPane = new JScrollPane();
        outerScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        outerScrollPane.setViewportBorder(BorderFactory.createRaisedBevelBorder());
        JPanel outerScrollPanel = new JPanel();
        outerScrollPanel.setLayout(new GridLayout(1,5));
        //outerScrollPanel.setPreferredSize(new Dimension(3200,600));
        outerScrollPanel.setPreferredSize(new Dimension(2000,600));
        outerScrollPane.setPreferredSize(new Dimension(1230,640));


        //setLayout(new GridLayout(1,5));

        //todoPanel
        JPanel todoPanel = new JPanel();
        todoPanel.setLayout(new BorderLayout());
        JLabel todoHeader = new JLabel("To-Do");
        //todoHeader.setPreferredSize(new Dimension(250,40));
        todoScrollPanel = new SprintBacklogScrollPanel(null,returnView,parentFrame);
        todoBacklogScrollPane = new BacklogScrollPane(100,100);
        todoBacklogScrollPane.setScrollPanel(todoScrollPanel);
        todoPanel.add(todoHeader,BorderLayout.NORTH);
        todoPanel.add(todoBacklogScrollPane,BorderLayout.CENTER);


        //inProgress panel
        JPanel inProgressPanel = new JPanel();
        inProgressPanel.setLayout(new BorderLayout());
        JLabel inProgressHeader = new JLabel("In Progress");
        inProgressScrollPanel = new SprintBacklogScrollPanel(null,returnView,parentFrame);
        inProgressBacklogScrollPane = new BacklogScrollPane(100,100);
        inProgressBacklogScrollPane.setScrollPanel(inProgressScrollPanel);
        inProgressPanel.add(inProgressHeader,BorderLayout.NORTH);
        inProgressPanel.add(inProgressBacklogScrollPane,BorderLayout.CENTER);

        //ready for review panel
        JPanel reviewPanel = new JPanel();
        reviewPanel.setLayout(new BorderLayout());
        JLabel reviewHeader = new JLabel("Ready for Review");
        reviewScrollPanel = new SprintBacklogScrollPanel(null,returnView,parentFrame);
        reviewBacklogScrollPane = new BacklogScrollPane(100,100);
        reviewBacklogScrollPane.setScrollPanel(reviewScrollPanel);
        reviewPanel.add(reviewHeader,BorderLayout.NORTH);
        reviewPanel.add(reviewBacklogScrollPane,BorderLayout.CENTER);

        //ready for tesing panel
        JPanel testingPanel = new JPanel();
        testingPanel.setLayout(new BorderLayout());
        JLabel testingHeader = new JLabel("Ready for Testing");
        testingScrollPanel = new SprintBacklogScrollPanel(null,returnView,parentFrame);
        testingBacklogScrollPane = new BacklogScrollPane(100,100);
        testingBacklogScrollPane.setScrollPanel(testingScrollPanel);
        testingPanel.add(testingHeader,BorderLayout.NORTH);
        testingPanel.add(testingBacklogScrollPane,BorderLayout.CENTER);

        //complete panel
        JPanel completePanel = new JPanel();
        completePanel.setLayout(new BorderLayout());
        JLabel completeHeader = new JLabel("Complete");
        completeScrollPanel = new SprintBacklogScrollPanel(null,returnView,parentFrame);
        completeBacklogScrollPane = new BacklogScrollPane(100,100);
        completeBacklogScrollPane.setScrollPanel(completeScrollPanel);
        completePanel.add(completeHeader,BorderLayout.NORTH);
        completePanel.add(completeBacklogScrollPane,BorderLayout.CENTER);


        //add layouts to panel
        outerScrollPanel.add(todoPanel);
        outerScrollPanel.add(inProgressPanel);
        outerScrollPanel.add(reviewPanel);
        outerScrollPanel.add(testingPanel);
        outerScrollPanel.add(completePanel);


        outerScrollPane.getViewport().add(outerScrollPanel);
        add(outerScrollPane);
    }

}
