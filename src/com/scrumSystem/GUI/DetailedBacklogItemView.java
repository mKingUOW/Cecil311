package com.scrumSystem.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Darryl on 10/05/2016.
 */
public class DetailedBacklogItemView extends JPanel {

    private JFrame parentFrame;
    private JPanel currentView;
    private JPanel returnView; //view to return to on close
    private MemberView parentPanel;

    private DescPanel descPanel;
    private BacklogScrollPane descScrollPane;
    private CommentsPanel commentsPanel;
    private BacklogScrollPane commentsScrollPane;


    public DetailedBacklogItemView(JFrame p, JPanel ret, MemberView pp){
        parentFrame = p;
        currentView = this;
        returnView = ret;
        parentPanel = pp;
        prepare();
    }

    public void prepare(){
        setLayout(new BorderLayout());

        //setup north layout
        JPanel northLayout = new JPanel();
        northLayout.setLayout(new GridLayout(1,2));
        JLabel leftHeader = new JLabel("Description");
        JPanel rightHeaderLayout = new JPanel();
        rightHeaderLayout.setLayout(new BorderLayout());
        JLabel rightHeader = new JLabel("Comments",SwingConstants.CENTER);
        rightHeaderLayout.add(rightHeader,BorderLayout.CENTER);
        final NavButton newCommentButton = new NavButton("Create Comment",parentPanel);
        newCommentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(newCommentButton.getText().equals("Create Comment")){
                    newCommentButton.setText("Submit Comment");
                    commentsPanel.addComment();
                }
                else if(newCommentButton.getText().equals("Submit Comment")){
                    newCommentButton.setText("Create Comment");
                    commentsPanel.submitComment(-1);
                    //save comments section?
                }

            }
        });
        rightHeaderLayout.add(newCommentButton,BorderLayout.EAST);
        northLayout.add(leftHeader);
        northLayout.add(rightHeaderLayout);

        //setup center layout
        JPanel centerLayout = new JPanel();
        centerLayout.setLayout(new GridLayout(1,2));

        //setup description panel
        //create Description panel for displaying an issues details
        descPanel = new DescPanel();
        descScrollPane = new BacklogScrollPane(100,100);
        descScrollPane.setScrollPanel(descPanel);
        centerLayout.add(descScrollPane);

        //setup comments panel
        commentsPanel = new CommentsPanel(parentPanel);
        commentsPanel.setBackground(Color.decode("#EBF0F2"));
        commentsScrollPane = new BacklogScrollPane(100,100);
        commentsScrollPane.setScrollPanel(commentsPanel);
        centerLayout.add(commentsScrollPane);

        //setup south layout
        JPanel southLayout = new JPanel();
        southLayout.setLayout(new GridBagLayout());

        //button to return to prev view
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.remove(currentView);
                parentFrame.add(returnView);
                parentFrame.revalidate();
                parentFrame.repaint();
                parentPanel.setCurrentView(returnView);
            }
        });
        exitButton.setPreferredSize(new Dimension(100,35));
        southLayout.add(exitButton);


        add(northLayout,BorderLayout.NORTH);
        add(centerLayout,BorderLayout.CENTER);
        add(southLayout,BorderLayout.SOUTH);

        setVisible(true);
    }
}
