package com.scrumSystem.GUI;

import com.scrumSystem.user.UserEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Darryl on 19-May-16.
 */
public class CreateNewUserView extends JPanel {

    private JFrame parentFrame;
    private MemberView parentPanel;

    public CreateNewUserView(JFrame p, MemberView pp){
        parentFrame = p;
        parentPanel = pp;
        prepare();
    }

    public void prepare(){
        setLayout(new GridLayout(9,1));

        //username panel
        JPanel panelOne = new JPanel();
        panelOne.setLayout(new GridBagLayout());
        JLabel usernameLabel = new JLabel("Username: ", SwingConstants.CENTER);
        final JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(100,35));
        panelOne.add(usernameLabel);
        panelOne.add(usernameField);

        //password panel
        JPanel panelTwo = new JPanel();
        panelTwo.setLayout(new GridBagLayout());
        JLabel passwordLabel = new JLabel("Password: ", SwingConstants.CENTER);
        final JTextField passwordField = new JTextField();
        passwordField.setPreferredSize(new Dimension(100,35));
        panelTwo.add(passwordLabel);
        panelTwo.add(passwordField);

        //user type panel
        JPanel panelThree = new JPanel();
        panelThree.setLayout(new GridBagLayout());
        JLabel typeLabel = new JLabel("User Type: ", SwingConstants.CENTER);
        String[] types = {"System Administrator","Scrum Master", "Product Owner", "Team Member" };
        final JComboBox<String> typeComboBox = new JComboBox<String>(types);
        typeComboBox.setPreferredSize(new Dimension(150,35));
        panelThree.add(typeLabel);
        panelThree.add(typeComboBox);

        //active project panel
        JPanel panelFour = new JPanel();
        panelFour.setLayout(new GridBagLayout());
        JLabel projLabel = new JLabel("Active Project: ", SwingConstants.CENTER);
        Vector projArray = new Vector(parentPanel.sc.getAllPorjectNames());
        projArray.insertElementAt("none",0);
        DefaultComboBoxModel projModel = new DefaultComboBoxModel(projArray);
        final JComboBox<String> projectsComboBox = new JComboBox<String>(projModel);
        projectsComboBox.setPreferredSize(new Dimension(150,35));
        panelFour.add(projLabel);
        panelFour.add(projectsComboBox);

        //first name panel
        JPanel panelFive = new JPanel();
        panelFive.setLayout(new GridBagLayout());
        JLabel fNameLabel = new JLabel("First name: ", SwingConstants.CENTER);
        final JTextField fNameField = new JTextField();
        fNameField.setPreferredSize(new Dimension(150,35));
        panelFive.add(fNameLabel);
        panelFive.add(fNameField);

        //last name panel
        JPanel panelSix = new JPanel();
        panelSix.setLayout(new GridBagLayout());
        JLabel lNameLabel = new JLabel("Last name: ");
        final JTextField lNameField = new JTextField();
        lNameField.setPreferredSize(new Dimension(150,35));
        panelSix.add(lNameLabel);
        panelSix.add(lNameField);

        //email panel
        JPanel panelSeven = new JPanel();
        panelSeven.setLayout(new GridBagLayout());
        JLabel emailLabel = new JLabel("Email: ");
        final JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(150,35));
        panelSeven.add(emailLabel);
        panelSeven.add(emailField);

        //skills panel
        JPanel panelEight = new JPanel();
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



        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        JButton createButton = new JButton("Create User");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                usernameField.setText("");
                passwordField.setText("");
                typeComboBox.setSelectedIndex(0);
                projectsComboBox.setSelectedIndex(0);
                fNameField.setText("");
                lNameField.setText("");
                emailField.setText("");
                model.removeAllElements();
            }
        });
        createButton.setPreferredSize(new Dimension(150,35));
        buttonPanel.add(createButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(parentFrame,"User will not be saved. Proceed? ","Cancel Creation of User",JOptionPane.OK_CANCEL_OPTION);
                if(res == JOptionPane.YES_OPTION){
                    //searchField.setText("");
                   // errorLabel.setText("");
                    usernameField.setText("");
                    passwordField.setText("");
                    typeComboBox.setSelectedIndex(0);
                    projectsComboBox.setSelectedIndex(0);
                    fNameField.setText("");
                    lNameField.setText("");
                    emailField.setText("");
                    model.removeAllElements();
                   // setPanelsVisible(false);
                }
            }
        });
        cancelButton.setPreferredSize(new Dimension(150,35));
        buttonPanel.add(cancelButton);


        add(panelOne);
        add(panelTwo);
        add(panelThree);
        add(panelFour);
        add(panelFive);
        add(panelSix);
        add(panelSeven);
        add(panelEight);
        add(buttonPanel);

    }

}
