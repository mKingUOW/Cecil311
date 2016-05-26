package com.scrumSystem.GUI;

import com.scrumSystem.project.ProjectDetails;
import com.scrumSystem.project.ProjectPOEntity;
import com.scrumSystem.project.ProjectTMEntity;
import com.scrumSystem.role.ScrumMaster;
import com.scrumSystem.user.UserEntity;

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
    private JPanel searchPanel;
    private JPanel currSprintPanel;


    private JTextField projNameField;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField pointField;
    private DefaultComboBoxModel<String> model;
    private JTextField durField;
    private JTextField currSprintField;


    private DefaultComboBoxModel poModel;
    private DefaultComboBoxModel availTMsModel;

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
                if(parentPanel.sc.getProjectDetails(searchField.getText()) != null){
                    setPanelsVisible(true);
                    errorLabel.setText("");

                    teamMembersScrollPanel.setCurrentProject(searchField.getText());
                    teamMembersScrollPanel.load();

                    ProjectDetails temp = parentPanel.sc.getProjectDetails(searchField.getText());
                    projNameField.setText(temp.getName());
                    startDateField.setText(temp.getStartDate());
                    endDateField.setText(temp.getEndDate());
                    pointField.setText(temp.getStoryPointValue());
                    currSprintField.setText(Integer.toString(temp.getCurrentSprint()));

                    //set scrum mester combo box
                    model.addElement(temp.getScrumMaster());
                    model.setSelectedItem(temp.getScrumMaster());
                    for(int i = 0; i<parentPanel.sc.getAvailableSMs().size(); i++){
                        model.addElement(parentPanel.sc.getAvailableSMs().get(i));
                    }

                    //set available product owner combobox
                    System.out.println("amtPOs : " + temp.getAvailablePOs().size());
                    for(int i = 0; i<temp.getAvailablePOs().size(); i++){
                        poModel.addElement(temp.getAvailablePOs().get(i));
                    }

                    //set available team member comboBox
                    for(int i = 0; i<temp.getAvailableTMs().size(); i++){
                        availTMsModel.addElement(temp.getAvailableTMs().get(i));
                    }

                    durField.setText(Integer.toString(temp.getDurationOfSprint()));
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

        if(parentPanel.sc.getUserRoleType().equals("SM")){
            searchField.setText(parentPanel.sc.getProjectName());
            searchField.setEditable(false);
        }

        //header panel
        JLabel header = new JLabel("Modify Existing Project",SwingConstants.CENTER);
        header.setPreferredSize(new Dimension(1000,100));
        add(header,BorderLayout.NORTH);

        //center panel
        JPanel centerLeftLayout = new JPanel();
        centerLeftLayout.setLayout(new GridLayout(9,1));

        centerLeftLayout.add(searchPanel);

        //proj name panel
        projNamePanel = new JPanel();
        projNamePanel.setLayout(new GridBagLayout());
        JLabel projNameLabel = new JLabel("Project Name: ");
        projNameField = new JTextField();
        projNameField.setPreferredSize(new Dimension(150,35));
        projNameField.setEditable(false);
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
        final JComboBox<String> smComboBox = new JComboBox<>(model);
        smPanel.add(smLabel);
        smPanel.add(smComboBox);
        centerLeftLayout.add(smPanel);

        //sprint duration panel
        durPanel = new JPanel();
        durPanel.setLayout(new GridBagLayout());
        JLabel durLabel = new JLabel("Sprint Duration (weeks): ");
        durField = new JTextField();
        durField.setPreferredSize(new Dimension(150,35));
        durPanel.add(durLabel);
        durPanel.add(durField);
        centerLeftLayout.add(durPanel);

        //current sprint panel
        currSprintPanel = new JPanel();
        currSprintPanel.setLayout(new GridBagLayout());
        JLabel currSprintLabel = new JLabel("Current Sprint: ");
        currSprintField = new JTextField();
        currSprintField.setPreferredSize(new Dimension(150,35));
        currSprintPanel.add(currSprintLabel);
        currSprintPanel.add(currSprintField);
        centerLeftLayout.add(currSprintPanel);


        //center right layout
        JPanel centerRightLayout = new JPanel();
        centerRightLayout.setLayout(new BorderLayout());

        //product owner selector panel
        poSelectPanel = new JPanel();
        poSelectPanel.setLayout(new GridBagLayout());
        JLabel poSelectLabel = new JLabel("Product Owner: ");
        Vector poArray = new Vector();
        poModel = new DefaultComboBoxModel(poArray);
        final JComboBox<String> poComboBox = new JComboBox<String>(poModel);
        poComboBox.setPreferredSize(new Dimension(150,35));
        JButton assignPOButton = new JButton("Assign");
        assignPOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                teamMembersScrollPanel.addElement((String)poComboBox.getSelectedItem());
                poModel.removeElement(poComboBox.getSelectedItem());
            }
        });
        poSelectPanel.add(poSelectLabel);
        poSelectPanel.add(poComboBox);
        poSelectPanel.add(assignPOButton);
        centerRightLayout.add(poSelectPanel,BorderLayout.NORTH);

        //team member assign panel
        tmAssignPanel = new JPanel();
        tmAssignPanel.setLayout(new BorderLayout());
        JPanel availPanel = new JPanel();
        availPanel.setLayout(new GridBagLayout());
        JLabel tmAssignLabel = new JLabel("Available Team Members: ");
        Vector availTMs = new Vector();
        availTMsModel = new DefaultComboBoxModel(availTMs);
        final JComboBox<String> availTmsComboBox = new JComboBox<String>(availTMsModel);
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
        teamMembersScrollPanel.setPOModel(poModel);
        teamMembersScrollPanel.setTmModel(availTMsModel);
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
                    poModel.removeAllElements();
                    currSprintField.setText("");
                    availTMsModel.removeAllElements();
                    teamMembersScrollPanel.clear();

                    setPanelsVisible(false);
                }
            }
        });

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //save to db
                ProjectDetails pd = parentPanel.sc.getProjectDetails(projNameField.getText());
                if(projNameField.getText().equals("")){
                    //alert error
                }
                else{
                    //pd.setName(projNameField.getText());
                }

                if(startDateField.getText().equals("")){
                    pd.setStartDate("null");
                }
                else{
                    pd.setStartDate(startDateField.getText());
                }

                if(endDateField.getText().equals("")){
                    pd.setEndDate("null");
                }
                else{
                    pd.setEndDate(endDateField.getText());
                }

                //reset prev scrum master active proj
                UserEntity prev = parentPanel.sc.getUser(pd.getScrumMaster());
                prev.setActiveProject("none");
                parentPanel.sc.modifyUser(prev);

                //set new scrum master active proj
                pd.setScrumMaster((String)smComboBox.getSelectedItem());
                UserEntity next = parentPanel.sc.getUser(pd.getScrumMaster());
                next.setActiveProject(pd.getName());
                parentPanel.sc.modifyUser(next);

                if(pointField.getText().equals("")){
                    pd.setStoryPointValue("null");
                }
                else{
                    pd.setStoryPointValue(pointField.getText());
                }

                if(durField.getText().equals("")){
                    pd.setDurationOfSprint(0);
                }
                else{
                    pd.setDurationOfSprint(Integer.parseInt(durField.getText()));
                }

                if(currSprintField.getText().equals("")){
                    pd.setCurrentSprint(0);
                }
                else{
                    pd.setCurrentSprint(Integer.parseInt(currSprintField.getText()));
                    parentPanel.sc.setCurrentSprint(Integer.parseInt(currSprintField.getText()));
                }




                teamMembersScrollPanel.setCurrentProject(searchField.getText());
                teamMembersScrollPanel.saveAssignedMembers();
                parentPanel.sc.saveProject(pd);

                //show alert
                JOptionPane.showMessageDialog(null,"Project Saved");
                projNameField.setText("");
                startDateField.setText("");
                endDateField.setText("");
                pointField.setText("");
                durField.setText("");
                model.removeAllElements();
                poModel.removeAllElements();
                availTMsModel.removeAllElements();
                teamMembersScrollPanel.clear();

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
        currSprintPanel.setVisible(state);
    }

}


class TeamMembersScrollPanel extends JPanel{
    private ArrayList<TeamMemberTextArea> tmUI;
    private ArrayList<UserEntity> tmData;

    private DefaultComboBoxModel poModel;
    private DefaultComboBoxModel tmModel;

    private JPanel currentView;
    private JFrame parentFrame;
    private MemberView parentPanel;

    private Boolean wasDoubleClick = false;
    private Timer timer;

    private String currentProject;


    public TeamMembersScrollPanel(JFrame pf, MemberView pp, JPanel curr){
        currentView = curr;
        parentFrame = pf;
        parentPanel = pp;
        tmUI = new ArrayList<TeamMemberTextArea>();
        tmData = new ArrayList<UserEntity>();
    }

    public void setPOModel(DefaultComboBoxModel p){
        poModel = p;
    }

    public void setTmModel(DefaultComboBoxModel t){
        tmModel = t;
    }

    public void setCurrentProject(String c){
        currentProject = c;
    }

    public void load(){
        //load all POs and TMs into ui

        ProjectPOEntity POs = parentPanel.sc.getPOsByProject(currentProject);
        ProjectTMEntity TMs = parentPanel.sc.getTMsByProject(currentProject);

        //add all po from array into ui
        for(int i = 0; i<POs.getPOs().size(); i++){
            addElement(POs.getPOs().get(i));
        }

        for(int i = 0; i<TMs.getTMs().size(); i++){
            addElement(TMs.getTMs().get(i));
        }


        //dummy data
        for(int i = 0; i<5; i++){
          //  addElement();
        }
        update();
        System.out.println("MPV431: tmUI size is "+ tmUI.size());

    }

    public void clear(){
        tmUI.clear();
        removeAll();
        revalidate();
        repaint();
    }


    public void addElement(String userID){

        //add users data to data array
        UserEntity user = parentPanel.sc.getUser(userID);
        tmData.add(user);


        //add users info to ui
        final TeamMemberTextArea temp = new TeamMemberTextArea(user);

        tmUI.add(temp);

        //use buffer to add additional dummy elements while list is small
        //keeps element sizes consistent and removes excess space at end of list.
        int buffer = 8 - tmUI.size();
        if(buffer > -1){
            setLayout(new GridLayout(buffer + tmUI.size(),1));
        }
        else{
            setLayout(new GridLayout(tmUI.size(),1));
        }


        //reference: http://stackoverflow.com/questions/548180/java-ignore-single-click-on-double-click
        temp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    //once removed, need to add to available tm array
                    if(temp.getUserEntity().getUserType().equals("PO")){
                        poModel.addElement(temp.getUserEntity().getUsername());
                    }
                    else if(temp.getUserEntity().getUserType().equals("TM")){
                        tmModel.addElement(temp.getUserEntity().getUsername());
                    }

                    //remove from tmUI array
                    for(int i = 0; i<tmUI.size(); i++){
                        if(tmUI.get(i).getUserEntity().getUsername().equals(temp.getUserEntity().getUsername())){
                            tmUI.remove(i);
                        }
                    }

                    remove(temp); //remove from ui
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

    public void saveAssignedMembers(){

        parentPanel.sc.clearPOsFromProject(currentProject);
        parentPanel.sc.clearTMsFromProject(currentProject);

        for(int i = 0; i<tmUI.size(); i++){
            UserEntity temp = tmUI.get(i).getUserEntity();

            if(temp.getUserType().equals("PO")){
                //check if already in db
                ArrayList<String> POs = parentPanel.sc.getPOsByProject(currentProject).getPOs();
                Boolean found = false;
                for(int a = 0 ; a < POs.size(); a++){
                    if(temp.getUsername().equals(POs.get(a))){
                        found = true;
                    }
                }

                if(found == false){
                    parentPanel.sc.assignPOtoProject(currentProject,temp.getUsername());
                }

            }
            else if(temp.getUserType().equals("TM")){

                ArrayList<String> TMs = parentPanel.sc.getTMsByProject(currentProject).getTMs();
                Boolean found = false;
                for(int a = 0 ; a < TMs.size(); a++){
                    if(temp.getUsername().equals(TMs.get(a))){
                        found = true;
                    }
                }

                if(found == false){
                    parentPanel.sc.assignTMtoProject(currentProject,temp.getUsername());
                }

            }
        }
    }
}

class TeamMemberTextArea extends JTextArea{

    private UserEntity ue;

    public TeamMemberTextArea(UserEntity e){
        super(3,50);
        ue = e;
        prepare();
    }

    public void prepare(){
        setEditable(false);
        setLineWrap(true);
        setBackground(Color.white);
        setOpaque(true);
        Border margin = new EmptyBorder(0,10,0,10);
        setBorder(new CompoundBorder(LineBorder.createGrayLineBorder(),margin));

        String text = "Role: " + ue.getUserType() + "\n" + "Username: " + ue.getUsername() + "\n" + "Name: " + ue.getFirstName() + " " + ue.getLastName();
        setText(text);
    }

    public UserEntity getUserEntity(){
        return ue;
    }


}