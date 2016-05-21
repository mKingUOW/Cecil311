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
public class ModifySprintView extends JPanel {

    private JFrame parentFrame;
    private JPanel returnView;
    private JPanel currentView;
    private MemberView parentPanel;

    public ModifySprintView(JFrame p, JPanel ret, MemberView pp){
        parentFrame = p;
        returnView = ret;
        currentView = this;
        parentPanel = pp;

        prepare();
    }

    public void prepare(){
        setLayout(new BorderLayout());

        //header
        JLabel headerLabel = new JLabel("Modify Sprint", SwingConstants.CENTER);
        headerLabel.setPreferredSize(new Dimension(100,40));


        //exit panel - south
        JPanel southLayout = new JPanel();
        southLayout.setLayout(new GridBagLayout());
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
        southLayout.add(exitButton);

        //center panel
        JPanel centerLayoutPanel = new JPanel();
        centerLayoutPanel.setLayout(new GridLayout(1,2));

        //center left panel
        JPanel centerLeftLayout = new JPanel();
        centerLeftLayout.setLayout(new BorderLayout());
        JLabel centerLeftHeader = new JLabel("Product Backlog", SwingConstants.CENTER);
        centerLeftLayout.add(centerLeftHeader,BorderLayout.NORTH);

        BacklogScrollPane pbScrollPane = new BacklogScrollPane(1000,1000);
        ModifySprintScrollPanel prodBacklogPanel = new ModifySprintScrollPanel(currentView,parentFrame,parentPanel);
        prodBacklogPanel.loadSprintBacklog();
        pbScrollPane.setScrollPanel(prodBacklogPanel);
        centerLeftLayout.add(pbScrollPane,BorderLayout.CENTER);
        centerLayoutPanel.add(centerLeftLayout);


        //center right panel
        JPanel centerRightLayout = new JPanel();
        centerRightLayout.setLayout(new BorderLayout());
        JLabel centerRightHeader = new JLabel("Sprint Backlog", SwingConstants.CENTER);
        centerRightLayout.add(centerRightHeader,BorderLayout.NORTH);
        BacklogScrollPane sbScrollPane = new BacklogScrollPane(1000,1000);
        ModifySprintScrollPanel sprintBacklogPanel = new ModifySprintScrollPanel(currentView,parentFrame,parentPanel);
        sbScrollPane.setScrollPanel(sprintBacklogPanel);
        centerRightLayout.add(sbScrollPane,BorderLayout.CENTER);
        centerLayoutPanel.add(centerRightLayout);


        //link scroll panels
        prodBacklogPanel.setPartner(sprintBacklogPanel);
        sprintBacklogPanel.setPartner(prodBacklogPanel);



        add(headerLabel,BorderLayout.NORTH);
        add(centerLayoutPanel,BorderLayout.CENTER);
        add(southLayout,BorderLayout.SOUTH);

    }
}

class ModifySprintScrollPanel extends JPanel{
    private ArrayList<JTextArea> sprintBacklogUI;
    private ArrayList<String> sprintBacklogData;

    private ModifySprintScrollPanel partnerPanel;
    private JPanel currentView;
    private JFrame parentFrame;
    private MemberView parentPanel;

    private Boolean wasDoubleClick = false;
    private Timer timer;

    public ModifySprintScrollPanel(JPanel curr, JFrame p, MemberView pp){
        partnerPanel = null;
        parentPanel = pp;
        currentView = curr;
        parentFrame = p;
        sprintBacklogUI = new ArrayList<JTextArea>();
        sprintBacklogData = new ArrayList<String>();
    }

    public void setPartner(ModifySprintScrollPanel p){
        partnerPanel = p;
    }

    public void loadSprintBacklog(){
        //load sprint backlog data from DB into array
        System.out.println("Loading");
        sprintBacklogData.add("One");
        sprintBacklogData.add("Two");
        sprintBacklogData.add("Three");
        sprintBacklogData.add("Four");

        //add all elements from array into ui
        for(int i = 0; i<sprintBacklogData.size(); i++){
            addElement(sprintBacklogData.get(i));
        }

        updateView();
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
                    DetailedBacklogItemView detailedBacklogItemView = new DetailedBacklogItemView(parentFrame,parentPanel.getCurrentView(),parentPanel);
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
                                partnerPanel.addElement(temp.getText());
                                partnerPanel.updateView();
                                remove(temp);
                                updateView();
                            }
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }

            }
        });

        add(temp);
        updateView();
    }

    public void updateView(){
        revalidate();
        repaint();
    }


}
