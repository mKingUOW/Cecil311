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
 * Created by Darryl on 10/05/2016.
 */
public class SprintBacklogView extends JPanel{

    private JFrame parentFrame;
    private JPanel currentView;
    private MemberView parentPanel;

    private JPanel leftLayoutPanel;
    private JPanel rightLayoutPanel;

    private SprintBacklogScrollPanel sprintBacklogScrollPanel;
    private UserBacklogScrollPanel userBacklogScrollPanel;
    private BacklogScrollPane sprintBacklogScrollPane;
    private BacklogScrollPane userBacklogScrollPane;

    public SprintBacklogView(JFrame f, JPanel curr, MemberView pp){
        parentFrame = f;
        currentView = this;
        parentPanel = pp;
        prepare();
    }

    public void prepare(){
        //set layout of sprintBacklogView
        setLayout(new GridLayout(1,2));

        //setup leftLayoutPanel
        leftLayoutPanel = new JPanel();
        leftLayoutPanel.setLayout(new BorderLayout());

        //add leftLayoutPanel header
        JLabel leftHeader = new JLabel("Sprint Backlog");
        leftLayoutPanel.add(leftHeader,BorderLayout.NORTH);

        //add sprint backlog to leftLayoutPanel
        userBacklogScrollPanel = new UserBacklogScrollPanel();
        sprintBacklogScrollPanel = new SprintBacklogScrollPanel(userBacklogScrollPanel,currentView,parentFrame,parentPanel);
        sprintBacklogScrollPane = new BacklogScrollPane(600,610);
        sprintBacklogScrollPane.setScrollPanel(sprintBacklogScrollPanel); //reference to user backlog
        leftLayoutPanel.add(sprintBacklogScrollPane,BorderLayout.CENTER);

        //setup right layout panel
        rightLayoutPanel = new JPanel();
        rightLayoutPanel.setLayout(new BorderLayout());

        //add rightLayoutPanel header
        JLabel rightHeader = new JLabel("User Backlog");
        rightLayoutPanel.add(rightHeader,BorderLayout.NORTH);

        //add user backlog to rightLayoutPanel
        userBacklogScrollPanel.setSprintBacklogPanel(sprintBacklogScrollPanel); //reference back to sprint backlog
        userBacklogScrollPane = new BacklogScrollPane(600,610);
        userBacklogScrollPane.setScrollPanel(userBacklogScrollPanel);
        rightLayoutPanel.add(userBacklogScrollPane,BorderLayout.CENTER);

        //add leftLayoutPanel and rightLayoutPanel to sprintBacklogPanel
        add(leftLayoutPanel);
        add(rightLayoutPanel);
    }
}

class UserBacklogScrollPanel extends JPanel{

    private ArrayList<JTextArea> userBacklogUI;
    private ArrayList<String> userBacklogData;

    private SprintBacklogScrollPanel sprintBacklogScrollPanel;


    public UserBacklogScrollPanel(){
        userBacklogUI = new ArrayList<JTextArea>();
        userBacklogData = new ArrayList<String>();
        loadUserBacklog();
    }

    public void loadUserBacklog(){
        //load user backlog from DB into array

        //load all elements in array into UI
        for(int i = 0; i<userBacklogData.size();i++){
            addElement(userBacklogData.get(i));
        }

        //repaint
        revalidate();
        repaint();
    }

    public void addElement(String data){
        final JTextArea temp = new JTextArea(3,50);
        temp.setText(data);
        temp.setEditable(false);
        temp.setLineWrap(true);
        temp.setBackground(Color.white);
        temp.setOpaque(true);
        Border margin = new EmptyBorder(0,10,0,10);
        temp.setBorder(new CompoundBorder(LineBorder.createGrayLineBorder(),margin));

        userBacklogUI.add(temp);

        //use buffer to add additional dummy elements while list is small
        //keeps element sizes consistent and removes excess space at end of list.
        int buffer = 8 - userBacklogUI.size();
        if(buffer > -1){
            setLayout(new GridLayout(buffer + userBacklogUI.size(),1));
        }
        else{
            setLayout(new GridLayout(userBacklogUI.size(),1));
        }


        temp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //descPanel.displayIssue(temp.getText());
                sprintBacklogScrollPanel.addElement(temp.getText());
                sprintBacklogScrollPanel.update();
                remove(temp);
                update();
            }
        });

        add(temp);
        //revalidate();
        //repaint();
    }

    public void update(){
        revalidate();
        repaint();
    }

    public void setSprintBacklogPanel(SprintBacklogScrollPanel s){
        sprintBacklogScrollPanel = s;
    }


}


class SprintBacklogScrollPanel extends JPanel{

    private ArrayList<JTextArea> sprintBacklogUI;
    private ArrayList<String> sprintBacklogData;

    private UserBacklogScrollPanel userBacklogPanel;
    private JPanel currentView;
    private JFrame parentFrame;
    private MemberView parentPanel;

    private Boolean wasDoubleClick = false;
    private Timer timer;

    public SprintBacklogScrollPanel(UserBacklogScrollPanel ubl, JPanel curr, JFrame p, MemberView pp){
        userBacklogPanel = ubl;
        currentView = curr;
        parentFrame = p;
        parentPanel = pp;
        sprintBacklogUI = new ArrayList<JTextArea>();
        sprintBacklogData = new ArrayList<String>();
        loadSprintBacklog();
    }

    public void loadSprintBacklog(){
        //load sprint backlog data from DB into array
        sprintBacklogData.add("One");
        sprintBacklogData.add("Two");
        sprintBacklogData.add("Three");
        sprintBacklogData.add("Four");

        //add all elements from array into ui
        for(int i = 0; i<sprintBacklogData.size(); i++){
            addElement(sprintBacklogData.get(i));
        }

        //repaint
        revalidate();
        repaint();

    }

    public void addElement(String data){
        final JTextArea temp = new JTextArea(3,50);
        temp.setText(data);
        temp.setEditable(false);
        temp.setLineWrap(true);
        temp.setBackground(Color.white);
        temp.setOpaque(true);
        Border margin = new EmptyBorder(0,10,0,10);
        temp.setBorder(new CompoundBorder(LineBorder.createGrayLineBorder(),margin));

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
                    parentFrame.remove(currentView);
                    parentFrame.add(detailedBacklogItemView);
                    parentFrame.revalidate();
                    parentFrame.repaint();

                    wasDoubleClick = true;
                }else{
                    Integer timerinterval = (Integer) Toolkit.getDefaultToolkit().getDesktopProperty( "awt.multiClickInterval");
                    timer = new Timer(timerinterval.intValue(), new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            if (wasDoubleClick) {
                                wasDoubleClick = false; // reset flag
                            } else {
                                userBacklogPanel.addElement(temp.getText());
                                userBacklogPanel.update();
                                remove(temp);
                                update();
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
