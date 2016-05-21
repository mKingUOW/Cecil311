package com.scrumSystem.GUI;

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

/**
 * Created by Darryl on 18-May-16.
 */
public class SprintManagementView extends JPanel{

    private JFrame parentFrame;
    private MemberView parentPanel;
    //private JPanel returnView;
    private SprintManagementScrollPanel sprintManagementScrollPanel;
    private BacklogScrollPane backlogScrollPane;

    public SprintManagementView(JFrame pf, MemberView pp){
        parentFrame = pf;
        parentPanel = pp;
        prepare();
    }

    public void prepare(){
        setLayout(new BorderLayout());

        //north panel
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(2,1));
        JLabel headerLabel = new JLabel("Sprint Management", SwingConstants.CENTER);
        headerLabel.setPreferredSize(new Dimension(100,40));
        northPanel.add(headerLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        JButton createSprintButton = new JButton("Create Sprint");
        createSprintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sprintManagementScrollPanel.addElement();
            }
        });
        buttonPanel.add(createSprintButton);
        //northPanel.add(buttonPanel,BorderLayout.EAST);

        add(northPanel, BorderLayout.NORTH);

        //center panel
        backlogScrollPane = new BacklogScrollPane(300,300);
        sprintManagementScrollPanel = new SprintManagementScrollPanel(parentFrame,parentPanel,this);
        backlogScrollPane.setScrollPanel(sprintManagementScrollPanel);
        add(backlogScrollPane,BorderLayout.CENTER);

    }
}

class SprintManagementScrollPanel extends JPanel{
    private ArrayList<JTextArea> sprintListUI;
    private ArrayList<String> sprintData;

    private JPanel currentView;
    private JFrame parentFrame;
    private MemberView parentPanel;

    public SprintManagementScrollPanel(JFrame pf, MemberView pp, JPanel curr){
        currentView = curr;
        parentFrame = pf;
        parentPanel = pp;
        sprintListUI = new ArrayList<JTextArea>();
        sprintData = new ArrayList<String>();
        loadSprints();
    }

    public void loadSprints(){
        //load sprint data from DB into sprintData array

        //add all elements from array into ui
        for(int i = 0; i<sprintData.size(); i++){
            addElement();
        }

        //dummy data
        for(int i = 0; i<5; i++){
            addElement();
        }
       update();
    }


    public void addElement(){
        //CHECK HOW MANY SPRINTS IN PROJ, ADD ONLY IF CREATED sPRINTS < TOTAL SPRINTS


        final JTextArea temp = new JTextArea(3,50);
        temp.setText("Sprint " + (sprintListUI.size()+1) );
        temp.setEditable(false);
        temp.setLineWrap(true);
        temp.setBackground(Color.white);
        temp.setOpaque(true);
        Border margin = new EmptyBorder(0,10,0,10);
        temp.setBorder(new CompoundBorder(LineBorder.createGrayLineBorder(),margin));

        sprintListUI.add(temp);

        temp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //show sprint editor
                ModifySprintView modifySprintView = new ModifySprintView(parentFrame,currentView,parentPanel);
                parentFrame.remove(parentPanel.getCurrentView());
                parentFrame.add(modifySprintView);
                parentFrame.revalidate();
                parentFrame.repaint();
                parentPanel.setCurrentView(modifySprintView);
            }
        });

        //use buffer to add additional dummy elements while list is small
        //keeps element sizes consistent and removes excess space at end of list.
        int buffer = 8 - sprintListUI.size();
        if(buffer > -1){
            setLayout(new GridLayout(buffer + sprintListUI.size(),1));
        }
        else{
            setLayout(new GridLayout(sprintListUI.size(),1));
        }

        add(temp);
        update();
    }

    public void update(){
        revalidate();
        repaint();
    }
}