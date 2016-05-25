package com.scrumSystem.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Darryl on 11/05/2016.
 */
public class SprintReviewView extends JPanel {

    private JFrame parentFrame;
    private MemberView parentPanel;
    private JPanel returnView;

    public SprintReviewView(JFrame f, JPanel r,MemberView pp){
        parentFrame = f;
        parentPanel = pp;
        returnView = r;
        prepare();
    }

    public void prepare(){
        //outer scroll pane and outer scroll panel
        JScrollPane outerScrollPane = new JScrollPane();
        outerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        outerScrollPane.setViewportBorder(BorderFactory.createRaisedBevelBorder());
        JPanel outerScrollPanel = new JPanel();
        outerScrollPanel.setLayout(new GridLayout(2,1));
        outerScrollPanel.setPreferredSize(new Dimension(640,1320)); //this is determining space to comments section for some reason??
        outerScrollPane.setPreferredSize(new Dimension(1230,640));


        //REVIEW SECTION LAYPOUT PANEL - holds completed/uncompleted
        JPanel reviewLayoutPanel = new JPanel();
        reviewLayoutPanel.setLayout(new GridLayout(1,2));

        //completed layout panel - LHS of reviewLaout
        JPanel completedLayoutPanel = new JPanel();
        JLabel completedHeader = new JLabel("Completed Issues");
        completedLayoutPanel.add(completedHeader,BorderLayout.NORTH);

        //completed scroll pane
        ScrollPanel completedPanel = new ScrollPanel(null);
        BacklogScrollPane completedScrollPane = new BacklogScrollPane(600,600);
        completedScrollPane.setScrollPanel(completedPanel);
        completedLayoutPanel.add(completedScrollPane,BorderLayout.CENTER);

        //uncompleted layout panel - RHS of review layout
        JPanel uncompletedLayoutPanel = new JPanel();
        JLabel uncompletedHeader = new JLabel("Uncompleted Issues");
        uncompletedLayoutPanel.add(uncompletedHeader,BorderLayout.NORTH);

        //uncompleted scroll pane
        ScrollPanel uncompletedPanel = new ScrollPanel(null);
        BacklogScrollPane uncompletedScrollPane = new BacklogScrollPane(600,600);
        uncompletedScrollPane.setScrollPanel(uncompletedPanel);
        uncompletedLayoutPanel.add(uncompletedScrollPane,BorderLayout.CENTER);

        //add completedLayout and uncompletedLayout to reviewLayout
        reviewLayoutPanel.add(completedLayoutPanel);
        reviewLayoutPanel.add(uncompletedLayoutPanel);

        //add reviewLayout to outerScollPanel
        outerScrollPanel.add(reviewLayoutPanel);

        //RETROSPECTIVE LAYOUT SECTION - holds comment section
        JPanel commentsLayoutPanel = new JPanel();
        JLabel commentsHeader = new JLabel("Sprint Review Comments");
        commentsLayoutPanel.add(commentsHeader,BorderLayout.NORTH);

        CommentsPanel commentsPanel = new CommentsPanel(parentPanel);
        BacklogScrollPane commentsBacklogScrollPane = new BacklogScrollPane(1210,640);
        commentsBacklogScrollPane.setScrollPanel(commentsPanel);
        commentsLayoutPanel.add(commentsBacklogScrollPane,BorderLayout.CENTER);
        outerScrollPanel.add(commentsLayoutPanel);

        outerScrollPane.getViewport().add(outerScrollPanel);
        add(outerScrollPane);
    }

}
