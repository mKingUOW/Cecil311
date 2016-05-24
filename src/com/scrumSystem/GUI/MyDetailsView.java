package com.scrumSystem.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Created by Darryl on 23/05/2016.
 */
public class MyDetailsView extends JPanel {

    private JFrame parentFrame;
    private MemberView parentPanel;

    public MyDetailsView(JFrame p, MemberView pp){
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
        JLabel usernameField = new JLabel(parentPanel.getUsername());
        panelOne.add(usernameLabel);
        panelOne.add(usernameField);

        //password panel
        JPanel panelTwo = new JPanel();
        panelTwo.setLayout(new GridBagLayout());
        JLabel passwordLabel = new JLabel("Password: ", SwingConstants.CENTER);
        JLabel passwordField = new JLabel(parentPanel.getPassword()); //get users password
        panelTwo.add(passwordLabel);
        panelTwo.add(passwordField);

        //user type panel
        JPanel panelThree = new JPanel();
        panelThree.setLayout(new GridBagLayout());
        JLabel typeLabel = new JLabel("User Type: ", SwingConstants.CENTER);
        JLabel typeField = new JLabel(); //get users type
        panelThree.add(typeLabel);
        panelThree.add(typeField);

        //active project panel
        JPanel panelFour = new JPanel();
        panelFour.setLayout(new GridBagLayout());
        JLabel projLabel = new JLabel("Active Project: ", SwingConstants.CENTER);
        JLabel projField = new JLabel();//get users active project name
        panelFour.add(projLabel);
        panelFour.add(projField);

        //first name panel
        JPanel panelFive = new JPanel();
        panelFive.setLayout(new GridBagLayout());
        JLabel fNameLabel = new JLabel("First name: ", SwingConstants.CENTER);
        JLabel fNameField = new JLabel(parentPanel.getFName()); //get users fName
        panelFive.add(fNameLabel);
        panelFive.add(fNameField);

        //last name panel
        JPanel panelSix = new JPanel();
        panelSix.setLayout(new GridBagLayout());
        JLabel lNameLabel = new JLabel("Last name: ");
        JLabel lNameField = new JLabel(); //get users lName
        panelSix.add(lNameLabel);
        panelSix.add(lNameField);

        //email panel
        JPanel panelSeven = new JPanel();
        panelSeven.setLayout(new GridBagLayout());
        JLabel emailLabel = new JLabel("Email: ");
        JLabel emailField = new JLabel(); //get users email
        panelSeven.add(emailLabel);
        panelSeven.add(emailField);

        //skills panel
        JPanel panelEight = new JPanel();
        panelEight.setLayout(new GridBagLayout());
        JLabel skillsLabel = new JLabel("Skills: ");
        Vector skillArray = new Vector();
        DefaultComboBoxModel model = new DefaultComboBoxModel(skillArray);
        JComboBox<String> skillsComboBox = new JComboBox<String>(model);
        skillsComboBox.setPreferredSize(new Dimension(150,35));

        //populate model with users skills

        panelEight.add(skillsLabel);
        panelEight.add(skillsComboBox);



        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        JButton editButton = new JButton("Edit Details");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModifyUserView modifyUserView = new ModifyUserView(parentFrame,parentPanel);
                parentFrame.remove(parentPanel.getCurrentView());
                parentFrame.add(modifyUserView);
                parentFrame.revalidate();
                parentFrame.repaint();
                parentPanel.setCurrentView(modifyUserView);
            }
        });
        editButton.setPreferredSize(new Dimension(150,35));
        buttonPanel.add(editButton);

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
