package com.scrumSystem.GUI;

import com.scrumSystem.project.sprintBacklog.SprintBacklogEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
        SprintReviewScrollPanel completedPanel = new SprintReviewScrollPanel(null,this,parentFrame,parentPanel);
        completedPanel.loadCompleted(parentPanel.sc.getCurrentSprint()-1);
        BacklogScrollPane completedScrollPane = new BacklogScrollPane(600,600);
        completedScrollPane.setScrollPanel(completedPanel);
        completedLayoutPanel.add(completedScrollPane,BorderLayout.CENTER);

        //uncompleted layout panel - RHS of review layout
        JPanel uncompletedLayoutPanel = new JPanel();
        JLabel uncompletedHeader = new JLabel("Uncompleted Issues");
        uncompletedLayoutPanel.add(uncompletedHeader,BorderLayout.NORTH);

        //uncompleted scroll pane
        SprintReviewScrollPanel uncompletedPanel = new SprintReviewScrollPanel(null,this,parentFrame,parentPanel);
        uncompletedPanel.loadIncomplete(parentPanel.sc.getCurrentSprint()-1);
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

        final CommentsPanel commentsPanel = new CommentsPanel(parentPanel);
        commentsPanel.loadReviewComments(parentPanel.sc.getCurrentSprint()-1);
        BacklogScrollPane commentsBacklogScrollPane = new BacklogScrollPane(1210,640);
        commentsBacklogScrollPane.setScrollPanel(commentsPanel);


        //header panel
        JPanel commentsHeaderLayout = new JPanel();
        commentsHeaderLayout.setLayout(new BorderLayout());
        JLabel commentsHeader = new JLabel("Sprint Review Comments",SwingConstants.CENTER);
        final JButton addCommentButton = new JButton("Create Comment");
        addCommentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(addCommentButton.getText().equals("Create Comment")){
                    addCommentButton.setText("Submit Comment");
                    commentsPanel.addComment();
                }
                else if(addCommentButton.getText().equals("Submit Comment")){
                    addCommentButton.setText("Create Comment");
                    commentsPanel.submitComment(-1);
                    //save comments section?
                }

            }
        });
        commentsHeaderLayout.add(commentsHeader, BorderLayout.CENTER);
        commentsHeaderLayout.add(addCommentButton,BorderLayout.EAST);
        commentsLayoutPanel.add(commentsHeaderLayout,BorderLayout.NORTH);

        commentsLayoutPanel.add(commentsBacklogScrollPane,BorderLayout.CENTER);


        outerScrollPanel.add(commentsLayoutPanel);
        outerScrollPane.getViewport().add(outerScrollPanel);
        add(outerScrollPane);
    }

}

class SprintReviewScrollPanel extends MyScollPanel{

    private ArrayList<JTextArea> sprintBacklogUI;
    private ArrayList<String> sprintBacklogData;

    private UserBacklogScrollPanel userBacklogPanel;
    private JPanel currentView;
    private JFrame parentFrame;
    private MemberView parentPanel;

    private Boolean wasDoubleClick = false;
    private Timer timer;

    public SprintReviewScrollPanel(UserBacklogScrollPanel ubl, JPanel curr, JFrame p, MemberView pp){
        userBacklogPanel = ubl;
        currentView = curr;
        parentFrame = p;
        parentPanel = pp;
        sprintBacklogUI = new ArrayList<JTextArea>();
        sprintBacklogData = new ArrayList<String>();
    }

    public void loadCompleted(int sid){
        //load sprint backlog data from DB into array
        removeAll();
        System.out.println(parentPanel.sc.getProjectName() + " " + sid);
        ArrayList<SprintBacklogEntity> bls = parentPanel.sc.getCompletedFromSprint(parentPanel.sc.getProjectName(),sid);
        for(int i = 0; i<bls.size(); i++){
            addElement(bls.get(i));
        }
        update();
    }

    public void loadIncomplete(int sid){
        //load sprint backlog data from DB into array
        removeAll();
        ArrayList<SprintBacklogEntity> bls = parentPanel.sc.getIncompleteFromSprint(sid);
        for(int i = 0; i<bls.size(); i++){
            addElement(bls.get(i));
        }
        update();
    }

    public void addElement(SprintBacklogEntity s){
        s.setAssignedUser("none");
        parentPanel.sc.modifySprintBL(s);
        final SprintBLTextArea temp = new SprintBLTextArea(s);


        sprintBacklogUI.add(temp);

        //use buffer to add additional dummy elements while list is small
        //keeps element sizes consistent and removes excess space at end of list.
        int buffer = 8 - sprintBacklogUI.size();
        if(buffer > -1){
            setLayout(new GridLayout(buffer + sprintBacklogUI.size(),1));
        }
        else{
            setLayout(new GridLayout(sprintBacklogUI.size(),1));
        }


        //reference: http://stackoverflow.com/questions/548180/java-ignore-single-click-on-double-click
        temp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    System.out.println("double clicked");
                    DetailedBacklogItemView detailedBacklogItemView = new DetailedBacklogItemView(parentFrame,currentView,parentPanel);
                    detailedBacklogItemView.setSBE(temp.getSBE());
                    detailedBacklogItemView.displayDesc();
                    detailedBacklogItemView.displayComments();
                    parentFrame.remove(parentPanel.getCurrentView());
                    parentFrame.add(detailedBacklogItemView);
                    parentFrame.revalidate();
                    parentFrame.repaint();
                    parentPanel.setCurrentView(detailedBacklogItemView);

                    wasDoubleClick = true;
                }else{
                    Integer timerinterval = (Integer) Toolkit.getDefaultToolkit().getDesktopProperty( "awt.multiClickInterval");
                    timer = new Timer(timerinterval.intValue(), new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            if (wasDoubleClick) {
                                wasDoubleClick = false; // reset flag
                            } else {
                                /*
                                temp.getSBE().setAssignedUser(parentPanel.getUsername());
                                parentPanel.sc.modifySprintBL(temp.getSBE());
                                userBacklogPanel.addElement(temp.getSBE());
                                userBacklogPanel.update();
                                remove(temp);
                                update();
                                */
                            }
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }

            }
        });

        add(temp);
    }

    public void update(){
        revalidate();
        repaint();
    }

}
