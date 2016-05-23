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
 * Created by Darryl on 11/05/2016.
 */
public class SprintBoardView extends JPanel{

    private JFrame parentFrame;
    private JPanel returnView;
    private MemberView parentPanel;

    private SprintBoardScrollPanel todoScrollPanel;
    private SprintBoardScrollPanel inProgressScrollPanel;
    private SprintBoardScrollPanel reviewScrollPanel;
    private SprintBoardScrollPanel testingScrollPanel;
    private SprintBoardScrollPanel completeScrollPanel;

    private BacklogScrollPane todoBacklogScrollPane;
    private BacklogScrollPane inProgressBacklogScrollPane;
    private BacklogScrollPane reviewBacklogScrollPane;
    private BacklogScrollPane testingBacklogScrollPane;
    private BacklogScrollPane completeBacklogScrollPane;

    public SprintBoardView(JFrame p, JPanel ret, MemberView pp){
        parentFrame = p;
        returnView = ret;
        parentPanel = pp;
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
        JPanel todoHeaderLayout = new JPanel();
        todoHeaderLayout.setLayout(new BorderLayout());
        JLabel todoHeader = new JLabel("To-Do",SwingConstants.CENTER);
        todoHeaderLayout.add(todoHeader,BorderLayout.CENTER);
        JButton todoRight = new JButton(">");
        todoRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                todoScrollPanel.moveRight();
            }
        });
        todoHeaderLayout.add(todoRight,BorderLayout.EAST);
        //todoHeader.setPreferredSize(new Dimension(250,40));
        todoScrollPanel = new SprintBoardScrollPanel(parentFrame,parentPanel);
        todoScrollPanel.loadUserBacklog();
        todoBacklogScrollPane = new BacklogScrollPane(100,100);
        todoBacklogScrollPane.setScrollPanel(todoScrollPanel);
        todoPanel.add(todoHeaderLayout,BorderLayout.NORTH);
        todoPanel.add(todoBacklogScrollPane,BorderLayout.CENTER);


        //inProgress panel
        JPanel inProgressPanel = new JPanel();
        inProgressPanel.setLayout(new BorderLayout());
        JPanel inProgHeaderLayout = new JPanel();
        inProgHeaderLayout.setLayout(new BorderLayout());
        JLabel inProgressHeader = new JLabel("In Progress",SwingConstants.CENTER);
        inProgHeaderLayout.add(inProgressHeader,BorderLayout.CENTER);
        JButton inProgLeft = new JButton("<");
        inProgLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inProgressScrollPanel.moveLeft();
            }
        });
        JButton inProgRight = new JButton(">");
        inProgRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inProgressScrollPanel.moveRight();
            }
        });
        JPanel ipbLayout = new JPanel();
        ipbLayout.setLayout(new GridLayout(1,2));
        ipbLayout.add(inProgLeft);
        ipbLayout.add(inProgRight);

        inProgHeaderLayout.add(ipbLayout,BorderLayout.EAST);
        inProgressScrollPanel = new SprintBoardScrollPanel(parentFrame, parentPanel);
        inProgressBacklogScrollPane = new BacklogScrollPane(100,100);
        inProgressBacklogScrollPane.setScrollPanel(inProgressScrollPanel);
        inProgressPanel.add(inProgHeaderLayout,BorderLayout.NORTH);
        inProgressPanel.add(inProgressBacklogScrollPane,BorderLayout.CENTER);

        //ready for review panel
        JPanel reviewPanel = new JPanel();
        reviewPanel.setLayout(new BorderLayout());
        JPanel reviewHeaderPanel = new JPanel();
        reviewHeaderPanel.setLayout(new BorderLayout());
        JLabel reviewHeader = new JLabel("Ready for Review",SwingConstants.CENTER);
        reviewHeaderPanel.add(reviewHeader, BorderLayout.CENTER);
        JPanel revButtonPanel = new JPanel();
        revButtonPanel.setLayout(new GridLayout(1,2));
        JButton reviewLeft = new JButton("<");
        reviewLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reviewScrollPanel.moveLeft();
            }
        });
        JButton reviewRight = new JButton(">");
        reviewRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reviewScrollPanel.moveRight();
            }
        });
        revButtonPanel.add(reviewLeft);
        revButtonPanel.add(reviewRight);
        reviewHeaderPanel.add(revButtonPanel,BorderLayout.EAST);

        reviewScrollPanel = new SprintBoardScrollPanel(parentFrame,parentPanel);
        reviewBacklogScrollPane = new BacklogScrollPane(100,100);
        reviewBacklogScrollPane.setScrollPanel(reviewScrollPanel);
        reviewPanel.add(reviewHeaderPanel,BorderLayout.NORTH);
        reviewPanel.add(reviewBacklogScrollPane,BorderLayout.CENTER);

        //ready for tesing panel
        JPanel testingPanel = new JPanel();
        testingPanel.setLayout(new BorderLayout());
        JPanel testHeaderLayout = new JPanel();
        testHeaderLayout.setLayout(new BorderLayout());
        JLabel testingHeader = new JLabel("Ready for Testing",SwingConstants.CENTER);
        testHeaderLayout.add(testingHeader,BorderLayout.CENTER);
        JPanel testButtonLayout = new JPanel();
        testButtonLayout.setLayout(new GridLayout(1,2));
        JButton testLeft = new JButton("<");
        testLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                testingScrollPanel.moveLeft();
            }
        });
        JButton testRight = new JButton(">");
        testRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                testingScrollPanel.moveRight();
            }
        });
        testButtonLayout.add(testLeft);
        testButtonLayout.add(testRight);
        testHeaderLayout.add(testButtonLayout,BorderLayout.EAST);

        testingScrollPanel = new SprintBoardScrollPanel(parentFrame,parentPanel);
        testingBacklogScrollPane = new BacklogScrollPane(100,100);
        testingBacklogScrollPane.setScrollPanel(testingScrollPanel);
        testingPanel.add(testHeaderLayout,BorderLayout.NORTH);
        testingPanel.add(testingBacklogScrollPane,BorderLayout.CENTER);

        //complete panel
        JPanel completePanel = new JPanel();
        completePanel.setLayout(new BorderLayout());
        JPanel compHeaderLayout = new JPanel();
        compHeaderLayout.setLayout(new BorderLayout());
        JLabel completeHeader = new JLabel("Complete",SwingConstants.CENTER);
        compHeaderLayout.add(completeHeader,BorderLayout.CENTER);
        JPanel compButtonLayout = new JPanel();
        compButtonLayout.setLayout(new GridLayout(1,2));
        JButton compLeft = new JButton("<");
        compLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                completeScrollPanel.moveLeft();
            }
        });
        compButtonLayout.add(compLeft);
        compHeaderLayout.add(compButtonLayout,BorderLayout.EAST);

        completeScrollPanel = new SprintBoardScrollPanel(parentFrame,parentPanel);
        completeBacklogScrollPane = new BacklogScrollPane(100,100);
        completeBacklogScrollPane.setScrollPanel(completeScrollPanel);
        completePanel.add(compHeaderLayout,BorderLayout.NORTH);
        completePanel.add(completeBacklogScrollPane,BorderLayout.CENTER);


        //add layouts to panel
        outerScrollPanel.add(todoPanel);
        outerScrollPanel.add(inProgressPanel);
        outerScrollPanel.add(reviewPanel);
        outerScrollPanel.add(testingPanel);
        outerScrollPanel.add(completePanel);


        //link up scroll panels
        todoScrollPanel.setRight(inProgressScrollPanel);

        inProgressScrollPanel.setLeft(todoScrollPanel);
        inProgressScrollPanel.setRight(reviewScrollPanel);

        reviewScrollPanel.setLeft(inProgressScrollPanel);
        reviewScrollPanel.setRight(testingScrollPanel);

        testingScrollPanel.setLeft(reviewScrollPanel);
        testingScrollPanel.setRight(completeScrollPanel);

        completeScrollPanel.setLeft(testingScrollPanel);

        outerScrollPane.getViewport().add(outerScrollPanel);
        add(outerScrollPane);
    }

}


class SprintBoardScrollPanel extends JPanel{
    private ArrayList<JTextArea> uiElementArray;
    private ArrayList<String> dataArray;

    private JFrame parentFrame;
    private MemberView parentPanel;

    private SprintBoardScrollPanel left;
    private SprintBoardScrollPanel right;

    private Boolean wasDoubleClick = false;
    private Timer timer;

    private JTextArea currSelected;

    public SprintBoardScrollPanel(JFrame p, MemberView pp){
        parentFrame = p;
        parentPanel = pp;
        uiElementArray = new ArrayList<JTextArea>();
        dataArray = new ArrayList<String>();
        left = null;
        right = null;
        //loadUserBacklog();
    }

    public void loadUserBacklog(){
        //load sprint backlog data from DB into array
        dataArray.add("One");
        dataArray.add("Two");
        dataArray.add("Three");
        dataArray.add("Four");

        //add all elements from array into ui
        for(int i = 0; i<dataArray.size(); i++){
            addElement(dataArray.get(i));
        }

        //repaint
        revalidate();
        repaint();

    }

    public void addElement(String data){
        final JTextArea temp = new JTextArea(4,30);
        temp.setText(data);
        temp.setEditable(false);
        temp.setLineWrap(true);
        temp.setBackground(Color.white);
        temp.setOpaque(true);
        Border margin = new EmptyBorder(0,10,0,10);
        temp.setBorder(new CompoundBorder(LineBorder.createGrayLineBorder(),margin));

        uiElementArray.add(temp);

        //use buffer to add additional dummy elements while list is small
        //keeps element sizes consistent and removes excess space at end of list.
        int buffer = 8 - uiElementArray.size();
        if(buffer > -1){
            setLayout(new GridLayout(buffer + uiElementArray.size(),1));
        }
        else{
            setLayout(new GridLayout(uiElementArray.size(),1));
        }


        //reference: http://stackoverflow.com/questions/548180/java-ignore-single-click-on-double-click
        temp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    System.out.println("double clicked");

                    DetailedBacklogItemView detailedBacklogItemView = new DetailedBacklogItemView(parentFrame,parentPanel.getCurrentView(),parentPanel);
                    parentFrame.remove(parentPanel.getCurrentView());
                    parentFrame.add(detailedBacklogItemView);
                    parentPanel.setCurrentView(detailedBacklogItemView);
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
                               //single click stuff
                                if(currSelected != temp){
                                    currSelected = temp;
                                    deselectAll();
                                    currSelected.setBackground(Color.lightGray);
                                }
                                else{
                                    currSelected.setBackground(Color.white);
                                    currSelected = null;
                                }

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

    public void setLeft(SprintBoardScrollPanel l){
        left = l;
    }

    public void setRight(SprintBoardScrollPanel r){
        right = r;
    }

    public void moveLeft(){
        if(left != null && currSelected != null){
            left.addElement(currSelected.getText());
            remove(currSelected);
            currSelected = null;
            update();
            left.update();
        }
    }

    public void moveRight(){
        if(right != null && currSelected != null){
            right.addElement(currSelected.getText());
            remove(currSelected);
            currSelected = null;
            update();
            right.update();
        }
    }

    public void deselectAll(){
        for(int i = 0; i<uiElementArray.size(); i++){
            uiElementArray.get(i).setBackground(Color.white);
        }

    }


}