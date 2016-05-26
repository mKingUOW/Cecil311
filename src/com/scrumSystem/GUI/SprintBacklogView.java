package com.scrumSystem.GUI;

import com.scrumSystem.project.sprintBacklog.SprintBacklogEntity;

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
        JPanel leftHeaderLayout = new JPanel();
        leftHeaderLayout.setLayout(new BorderLayout());
        JLabel leftHeader = new JLabel("Sprint Backlog",SwingConstants.CENTER);
        leftHeaderLayout.add(leftHeader,BorderLayout.CENTER);
        JButton createSBLButton = new JButton("Add Task");
        createSBLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateSprintBacklogItemView createSprintBacklogItemView = new CreateSprintBacklogItemView("CREATE",parentFrame,currentView,parentPanel,sprintBacklogScrollPanel);
                parentFrame.remove(parentPanel.getCurrentView());
                parentFrame.add(createSprintBacklogItemView);
                parentFrame.revalidate();
                parentFrame.repaint();
                parentPanel.setCurrentView(createSprintBacklogItemView);
            }
        });
        leftHeaderLayout.add(createSBLButton,BorderLayout.EAST);
        leftLayoutPanel.add(leftHeaderLayout,BorderLayout.NORTH);


        //add sprint backlog to leftLayoutPanel
        userBacklogScrollPanel = new UserBacklogScrollPanel(parentFrame,parentPanel,currentView);
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
    private MemberView parentPanel;
    private JFrame parentFrame;
    private JPanel currentView;

    private SprintBacklogScrollPanel sprintBacklogScrollPanel;

    private Boolean wasDoubleClick = false;
    private Timer timer;


    public UserBacklogScrollPanel(JFrame f,MemberView p, JPanel c){
        userBacklogUI = new ArrayList<JTextArea>();
        userBacklogData = new ArrayList<String>();
        parentPanel = p;
        parentFrame = f;
        currentView = c;

        load();
    }

    public void load(){
        //load sprint backlog data from DB into array
        removeAll();
        ArrayList<SprintBacklogEntity> bls = parentPanel.sc.getSprintBLsFromSprint(parentPanel.sc.getCurrentSprint());
        for(int i = 0; i<bls.size(); i++){
            if(bls.get(i).getAssignedUser().equals(parentPanel.getUsername())){
                addElement(bls.get(i));
            }
        }
        update();
    }

    public void addElement(SprintBacklogEntity s){
        final SprintBLTextArea temp = new SprintBLTextArea(s);

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
                                temp.getSBE().setAssignedUser("none");
                                parentPanel.sc.modifySprintBL(temp.getSBE());
                                sprintBacklogScrollPanel.addElement(temp.getSBE());
                                sprintBacklogScrollPanel.update();
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


class SprintBacklogScrollPanel extends MyScollPanel{

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
        load();
    }

    public void load(){
        //load sprint backlog data from DB into array
        removeAll();
        ArrayList<SprintBacklogEntity> bls = parentPanel.sc.getSprintBLsFromSprint(parentPanel.sc.getCurrentSprint());
        for(int i = 0; i<bls.size(); i++){
            if(bls.get(i).getAssignedUser().equals("none")){
                addElement(bls.get(i));
            }

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
                                temp.getSBE().setAssignedUser(parentPanel.getUsername());
                                parentPanel.sc.modifySprintBL(temp.getSBE());
                                userBacklogPanel.addElement(temp.getSBE());
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

class SprintBLTextArea extends JTextArea{

    private SprintBacklogEntity sbe;

    public SprintBLTextArea(SprintBacklogEntity s){
        super(3,50);
        sbe = s;
        prepare();
    }

    public void prepare(){
        //temp.setText(data);
        String text = "ID: " + sbe.getIssueID() + "\tStoryRef: " + sbe.getStoryLink() + "\tType: "+ sbe.getIssueType() + "\tStory Points: " + sbe.getStoryPoints() + "\t      Priority: " + sbe.getPriority()
                +"\n\n" + "Description: " + sbe.getDescription();
        setText(text);
        setEditable(false);
        setLineWrap(true);
        setBackground(Color.white);
        setOpaque(true);
        Border margin = new EmptyBorder(0,10,0,10);
        setBorder(new CompoundBorder(LineBorder.createGrayLineBorder(),margin));

    }

    public SprintBacklogEntity getSBE(){
        return  sbe;
    }
}
