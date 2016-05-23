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
import java.util.Vector;

/**
 * Created by Darryl on 22-May-16.
 */
public class ModifyProjectView extends JPanel{

    private JFrame parentFrame;
    private MemberView parentPanel;


    private JPanel projNamePanel;
    private JPanel startDatePanel;
    private JPanel endDatePanel;
    private JPanel pointPanel;
    private JPanel smPanel;
    private JPanel durPanel;
    private JPanel buttonPanel;
    private JPanel poSelectPanel;
    private JPanel tmAssignPanel;



    private JTextField projNameField;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField pointField;
    private DefaultComboBoxModel<String> model;
    private JTextField durField;
    private JPanel searchPanel;

    private TeamMembersScrollPanel teamMembersScrollPanel;

    public ModifyProjectView(JFrame p, MemberView pp){
        parentFrame = p;
        parentPanel = pp;

        prepare();
    }

    public void prepare(){
        setLayout(new BorderLayout());

        JPanel centerLayout = new JPanel();
        centerLayout.setLayout(new GridLayout(1,2));

        //search for user panel
        searchPanel = new JPanel();
        searchPanel.setLayout(new GridBagLayout());
        JLabel searchLabel = new JLabel("Search for a project: ", SwingConstants.LEFT);
        final JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(150,35));
        final JLabel errorLabel = new JLabel("",SwingConstants.RIGHT);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if found set details and display

                //bypass for now
                if(searchField.getText().equals("bypass")){
                    setPanelsVisible(true);
                    errorLabel.setText("");
                }
                else{
                    //if not found display error
                    errorLabel.setText("Project not found.");
                    setPanelsVisible(false);
                }
            }
        });
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(errorLabel);

        //header panel
        JLabel header = new JLabel("Modify Existing Project",SwingConstants.CENTER);
        header.setPreferredSize(new Dimension(1000,100));
        add(header,BorderLayout.NORTH);

        //center panel
        JPanel centerLeftLayout = new JPanel();
        centerLeftLayout.setLayout(new GridLayout(8,1));

        centerLeftLayout.add(searchPanel);

        //proj name panel
        projNamePanel = new JPanel();
        projNamePanel.setLayout(new GridBagLayout());
        JLabel projNameLabel = new JLabel("Project Name: ");
        projNameField = new JTextField();
        projNameField.setPreferredSize(new Dimension(150,35));
        projNamePanel.add(projNameLabel);
        projNamePanel.add(projNameField);
        centerLeftLayout.add(projNamePanel);

        //start date panel
        startDatePanel = new JPanel();
        startDatePanel.setLayout(new GridBagLayout());
        JLabel startDateLabel = new JLabel("Start Date: ");
        startDateField = new JTextField();
        startDateField.setPreferredSize(new Dimension(150,35));
        startDatePanel.add(startDateLabel);
        startDatePanel.add(startDateField);
        centerLeftLayout.add(startDatePanel);

        //end date panel
        endDatePanel = new JPanel();
        endDatePanel.setLayout(new GridBagLayout());
        JLabel endDateLabel = new JLabel("End Date: ");
        endDateField = new JTextField();
        endDateField.setPreferredSize(new Dimension(150,35));
        endDatePanel.add(endDateLabel);
        endDatePanel.add(endDateField);
        centerLeftLayout.add(endDatePanel);

        //story point panel
        pointPanel = new JPanel();
        pointPanel.setLayout(new GridBagLayout());
        JLabel pointLabel = new JLabel("Story Point Value: ");
        pointField = new JTextField();
        pointField.setPreferredSize(new Dimension(150,35));
        pointPanel.add(pointLabel);
        pointPanel.add(pointField);
        centerLeftLayout.add(pointPanel);

        //scrum master panel
        smPanel = new JPanel();
        smPanel.setLayout(new GridBagLayout());
        JLabel smLabel = new JLabel("Scrum Master: ");
        Vector smArray = new Vector();
        model = new DefaultComboBoxModel(smArray);
        JComboBox<String> smComboBox = new JComboBox<>(model);
        smPanel.add(smLabel);
        smPanel.add(smComboBox);
        centerLeftLayout.add(smPanel);

        //add dummy data to scrum master model
        model.addElement("sm1");
        model.addElement("sm2");

        //sprint duration panel
        durPanel = new JPanel();
        durPanel.setLayout(new GridBagLayout());
        JLabel durLabel = new JLabel("Sprint Duration (weeks): ");
        durField = new JTextField();
        durField.setPreferredSize(new Dimension(150,35));
        durPanel.add(durLabel);
        durPanel.add(durField);
        centerLeftLayout.add(durPanel);

        //center right layout
        JPanel centerRightLayout = new JPanel();
        centerRightLayout.setLayout(new BorderLayout());

        //product owner selector panel
        poSelectPanel = new JPanel();
        poSelectPanel.setLayout(new GridBagLayout());
        JLabel poSelectLabel = new JLabel("Product Owner: ");
        Vector poArray = new Vector();
        DefaultComboBoxModel poModel = new DefaultComboBoxModel(poArray);
        JComboBox<String> poComboBox = new JComboBox<String>(poModel);
        poComboBox.setPreferredSize(new Dimension(150,35));
        poModel.addElement("one");
        poModel.addElement("two");
        poSelectPanel.add(poSelectLabel);
        poSelectPanel.add(poComboBox);
        centerRightLayout.add(poSelectPanel,BorderLayout.NORTH);

        //team member assign panel
        tmAssignPanel = new JPanel();
        tmAssignPanel.setLayout(new BorderLayout());
        JPanel availPanel = new JPanel();
        availPanel.setLayout(new GridBagLayout());
        JLabel tmAssignLabel = new JLabel("Available Team Members: ");
        Vector availTMs = new Vector();
        DefaultComboBoxModel availTMsModel = new DefaultComboBoxModel(availTMs);
        JComboBox<String> availTmsComboBox = new JComboBox<String>(availTMsModel);
        availTMsModel.addElement("one");
        availTMsModel.addElement("Two");
        availTmsComboBox.setPreferredSize(new Dimension(150,35));
        JButton assignButton = new JButton("Assign");
        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                teamMembersScrollPanel.addElement((String)availTmsComboBox.getSelectedItem());
                availTMsModel.removeElement(availTmsComboBox.getSelectedItem());
            }
        });
        availPanel.add(tmAssignLabel);
        availPanel.add(availTmsComboBox);
        availPanel.add(assignButton);
        tmAssignPanel.add(availPanel,BorderLayout.NORTH);

        teamMembersScrollPanel = new TeamMembersScrollPanel(parentFrame,parentPanel,this);
        BacklogScrollPane backlogScrollPane = new BacklogScrollPane(1000,1000);
        backlogScrollPane.setScrollPanel(teamMembersScrollPanel);
        tmAssignPanel.add(backlogScrollPane,BorderLayout.CENTER);


        centerRightLayout.add(tmAssignPanel,BorderLayout.CENTER);

        centerLayout.add(centerLeftLayout);
        centerLayout.add(centerRightLayout);
        add(centerLayout,BorderLayout.CENTER);


        //button panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        JButton create = new JButton("Save");
        create.setPreferredSize(new Dimension(75,35));
        JButton cancel = new JButton("Cancel");
        cancel.setPreferredSize(new Dimension(75,35));

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(parentFrame,"Project will not be saved. Proceed? ","Cancel Creation of Project",JOptionPane.OK_CANCEL_OPTION);
                if(res == JOptionPane.YES_OPTION){
                    projNameField.setText("");
                    startDateField.setText("");
                    endDateField.setText("");
                    pointField.setText("");
                    durField.setText("");
                    model.removeAllElements();

                    setPanelsVisible(false);
                }
            }
        });

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //save user to db

                //show alert
                JOptionPane.showMessageDialog(null,"Project Saved");
                projNameField.setText("");
                startDateField.setText("");
                endDateField.setText("");
                pointField.setText("");
                durField.setText("");
                model.removeAllElements();

                setPanelsVisible(false);
            }
        });

        buttonPanel.add(create);
        buttonPanel.add(cancel);
        add(buttonPanel,BorderLayout.SOUTH);

        setPanelsVisible(false);


    }

    public void setPanelsVisible(Boolean state){
        projNamePanel.setVisible(state);
        startDatePanel.setVisible(state);
        endDatePanel.setVisible(state);
        pointPanel.setVisible(state);
        smPanel.setVisible(state);
        durPanel.setVisible(state);
        buttonPanel.setVisible(state);
        tmAssignPanel.setVisible(state);
        poSelectPanel.setVisible(state);
    }

}


class TeamMembersScrollPanel extends JPanel{
    private ArrayList<JTextArea> sprintListUI;
    private ArrayList<String> sprintData;

    private JPanel currentView;
    private JFrame parentFrame;
    private MemberView parentPanel;

    private Boolean wasDoubleClick = false;
    private Timer timer;


    public TeamMembersScrollPanel(JFrame pf, MemberView pp, JPanel curr){
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
           // addElement();
        }

        //dummy data
        for(int i = 0; i<5; i++){
          //  addElement();
        }
        update();
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

        sprintListUI.add(temp);

        //use buffer to add additional dummy elements while list is small
        //keeps element sizes consistent and removes excess space at end of list.
        int buffer = 8 - sprintListUI.size();
        if(buffer > -1){
            setLayout(new GridLayout(buffer + sprintListUI.size(),1));
        }
        else{
            setLayout(new GridLayout(sprintListUI.size(),1));
        }


        //reference: http://stackoverflow.com/questions/548180/java-ignore-single-click-on-double-click
        temp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    //once removed, need to add to available tm array
                    remove(temp);
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
                                //userBacklogPanel.addElement(temp.getText());
                                //userBacklogPanel.update();
                                //remove(temp);
                                //update();
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