package com.scrumSystem.GUI;

import com.scrumSystem.role.SystemAdmin;
import com.scrumSystem.user.UserEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Created by Darryl on 19-May-16.
 */
public class ModifyUserView extends JPanel {

    private JFrame parentFrame;
    private MemberView parentPanel;

    private JPanel searchPanel;
    private JPanel panelOne;
    private JPanel panelTwo;
    private JPanel panelThree;
    private JPanel panelFour;
    private JPanel panelFive;
    private JPanel panelSix;
    private JPanel panelSeven;
    private JPanel panelEight;
    private JPanel buttonPanel;

    private JLabel usernameField;
    private JTextField passwordField;
    private JComboBox<String> typeComboBox;
    private DefaultComboBoxModel projModel;
    private JTextField fNameField;
    private JTextField lNameField;
    private JTextField emailField;

    public ModifyUserView(JFrame p, MemberView pp){
        parentFrame = p;
        parentPanel = pp;
        prepare();
    }

    public void prepare(){
        setLayout(new GridLayout(10,1));

        usernameField = new JLabel();

        //search for user panel
        searchPanel = new JPanel();
        searchPanel.setLayout(new GridBagLayout());
        JLabel searchLabel = new JLabel("Search for a user: ", SwingConstants.LEFT);
        final JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(150,35));
        final JLabel errorLabel = new JLabel("",SwingConstants.RIGHT);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if found set details and display

                UserEntity user = parentPanel.sc.getUser(searchField.getText());
                if(user != null){
                    setPanelsVisible(true);
                    usernameField.setText(user.getUsername());
                    passwordField.setText(user.getPassword());
                    typeComboBox.setSelectedItem(user.getUserType());
                    projModel.setSelectedItem(user.getActiveProject());
                    fNameField.setText(user.getFirstName());
                    lNameField.setText(user.getLastName());
                    emailField.setText(user.getEmail());
                    //set skills field

                    errorLabel.setText("");
                }
                else{
                    //if not found display error
                    errorLabel.setText("User not found.");
                    setPanelsVisible(false);
                }

            }
        });
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(errorLabel);

        //username panel
        panelOne = new JPanel();
        panelOne.setLayout(new GridBagLayout());
        JLabel usernameLabel = new JLabel("Username: ", SwingConstants.CENTER);

        if(parentPanel instanceof SystemAdminView){
            //usernameField = new JLabel(searchField.getText());
        }
        else{
            usernameField = new JLabel(parentPanel.getUsername());
        }


        //final JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(100,35));
        panelOne.add(usernameLabel);
        panelOne.add(usernameField);

        //password panel
        panelTwo = new JPanel();
        panelTwo.setLayout(new GridBagLayout());
        JLabel passwordLabel = new JLabel("Password: ", SwingConstants.CENTER);
        passwordField = new JTextField();
        passwordField.setPreferredSize(new Dimension(100,35));
        panelTwo.add(passwordLabel);
        panelTwo.add(passwordField);

        //user type panel
        panelThree = new JPanel();
        panelThree.setLayout(new GridBagLayout());
        JLabel typeLabel = new JLabel("User Type: ", SwingConstants.CENTER);
        String[] types = {"System Administrator","Scrum Master", "Product Owner", "Team Member" };
        typeComboBox = new JComboBox<String>(types);
        typeComboBox.setPreferredSize(new Dimension(150,35));
        panelThree.add(typeLabel);
        panelThree.add(typeComboBox);

        //active project panel
        panelFour = new JPanel();
        panelFour.setLayout(new GridBagLayout());
        JLabel projLabel = new JLabel("Active Project: ", SwingConstants.CENTER);
        Vector projArray = new Vector(parentPanel.sc.getAllPorjectNames());
        projArray.insertElementAt("none",0);
        projModel = new DefaultComboBoxModel(projArray);
        final JComboBox<String> projectsComboBox = new JComboBox<String>(projModel);
        projectsComboBox.setPreferredSize(new Dimension(150,35));
        panelFour.add(projLabel);
        panelFour.add(projectsComboBox);




        //first name panel
        panelFive = new JPanel();
        panelFive.setLayout(new GridBagLayout());
        JLabel fNameLabel = new JLabel("First name: ", SwingConstants.CENTER);
        fNameField = new JTextField();
        fNameField.setPreferredSize(new Dimension(150,35));
        panelFive.add(fNameLabel);
        panelFive.add(fNameField);

        //last name panel
        panelSix = new JPanel();
        panelSix.setLayout(new GridBagLayout());
        JLabel lNameLabel = new JLabel("Last name: ");
        lNameField = new JTextField();
        lNameField.setPreferredSize(new Dimension(150,35));
        panelSix.add(lNameLabel);
        panelSix.add(lNameField);

        //email panel
        panelSeven = new JPanel();
        panelSeven.setLayout(new GridBagLayout());
        JLabel emailLabel = new JLabel("Email: ");
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(150,35));
        panelSeven.add(emailLabel);
        panelSeven.add(emailField);

        //skills panel
        panelEight = new JPanel();
        panelEight.setLayout(new GridBagLayout());
        JLabel skillsLabel = new JLabel("Skills: ");
        Vector skillArray = new Vector();
        final DefaultComboBoxModel model = new DefaultComboBoxModel(skillArray);
        JComboBox<String> skillsComboBox = new JComboBox<String>(model);
        skillsComboBox.setPreferredSize(new Dimension(150,35));
        final JButton newSkillButton = new JButton("Add Skill");
        newSkillButton.setPreferredSize(new Dimension(100,35));
        final JTextField newSkillField = new JTextField();
        newSkillField.setPreferredSize(new Dimension(200,35));
        newSkillField.setVisible(false);
        final JButton addSkillButton = new JButton("Save Skill");
        addSkillButton.setPreferredSize(new Dimension(100,35));
        addSkillButton.setVisible(false);

        newSkillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newSkillField.setVisible(true);
                addSkillButton.setVisible(true);
                newSkillButton.setVisible(false);
            }
        });

        addSkillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!newSkillField.getText().equals("")){
                    model.addElement(newSkillField.getText());
                }

                newSkillField.setText("");
                newSkillField.setVisible(false);
                addSkillButton.setVisible(false);
                newSkillButton.setVisible(true);
            }
        });

        panelEight.add(skillsLabel);
        panelEight.add(skillsComboBox);
        panelEight.add(newSkillButton);
        panelEight.add(newSkillField);
        panelEight.add(addSkillButton);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if (parentPanel instanceof SystemAdminView) {
                    //save user to db
                    UserEntity newUser = new UserEntity();
                    newUser.setUsername(usernameField.getText());
                    newUser.setPassword(passwordField.getText());

                    //set user type
                    String t = (String)typeComboBox.getSelectedItem();
                    if(t.equals("Team Member")){
                        newUser.setUserType("TM");
                    }
                    else if(t.equals("System Administrator")){
                        newUser.setUserType("SA");
                    }
                    else if(t.equals("Scrum Master")){
                        newUser.setUserType("SM");
                    }
                    else if(t.equals("Product Owner")){
                        newUser.setUserType("PO");
                    }

                    newUser.setActiveProject((String)projectsComboBox.getSelectedItem());
                    newUser.setFName(fNameField.getText());
                    newUser.setLastName(lNameField.getText());
                    newUser.setEmail(emailField.getText());
                    //set skills

                    parentPanel.sc.addUser(newUser);


                    //show alert
                    JOptionPane.showMessageDialog(null,"Changes Saved");

                    //reset fields
                    searchField.setText("");
                    errorLabel.setText("");
                    usernameField.setText("");
                    passwordField.setText("");
                    typeComboBox.setSelectedIndex(0);
                    projectsComboBox.setSelectedIndex(0);
                    fNameField.setText("");
                    lNameField.setText("");
                    emailField.setText("");
                    model.removeAllElements();
                    setPanelsVisible(false);

                }
                else{
                    parentFrame.remove(parentPanel.getCurrentView());
                    MyDetailsView myDetailsView = new MyDetailsView(parentFrame,parentPanel);
                    parentFrame.add(myDetailsView);
                    parentFrame.validate();
                    parentFrame.repaint();
                    parentPanel.setCurrentView(myDetailsView);
                }

            }
        });
        saveButton.setPreferredSize(new Dimension(150,35));
        buttonPanel.add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int res = JOptionPane.showConfirmDialog(parentFrame,"Changes will not be saved. Proceed? ","Cancel Modification of User",JOptionPane.OK_CANCEL_OPTION);

                if(parentPanel instanceof SystemAdminView){
                    if(res == JOptionPane.YES_OPTION){
                        searchField.setText("");
                        errorLabel.setText("");
                        usernameField.setText("");
                        passwordField.setText("");
                        typeComboBox.setSelectedIndex(0);
                        projectsComboBox.setSelectedIndex(0);
                        fNameField.setText("");
                        lNameField.setText("");
                        emailField.setText("");
                        model.removeAllElements();
                        setPanelsVisible(false);
                    }
                }
                else{
                    parentFrame.remove(parentPanel.getCurrentView());
                    MyDetailsView myDetailsView = new MyDetailsView(parentFrame,parentPanel);
                    parentFrame.add(myDetailsView);
                    parentFrame.validate();
                    parentFrame.repaint();
                    parentPanel.setCurrentView(myDetailsView);
                }

            }
        });
        cancelButton.setPreferredSize(new Dimension(150,35));
        buttonPanel.add(cancelButton);

        if(parentPanel instanceof SystemAdminView){
            add(searchPanel);
        }

        add(panelOne);
        add(panelTwo);
        add(panelThree);
        add(panelFour);
        add(panelFive);
        add(panelSix);
        add(panelSeven);
        add(panelEight);
        add(buttonPanel);

        if(parentPanel instanceof SystemAdminView){
            setPanelsVisible(false);
        }


    }

    public void setPanelsVisible(Boolean state){
        panelOne.setVisible(state);
        panelTwo.setVisible(state);
        panelThree.setVisible(state);
        panelFour.setVisible(state);
        panelFive.setVisible(state);
        panelSix.setVisible(state);
        panelSeven.setVisible(state);
        panelSix.setVisible(state);
        panelEight.setVisible(state);
        buttonPanel.setVisible(state);
    }

}
