package com.scrumSystem.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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



    private JTextField projNameField;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField pointField;
    private DefaultComboBoxModel<String> model;
    private JTextField durField;
    private JPanel searchPanel;

    public ModifyProjectView(JFrame p, MemberView pp){
        parentFrame = p;
        parentPanel = pp;

        prepare();
    }

    public void prepare(){
        setLayout(new BorderLayout());

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
        JPanel centerLayout = new JPanel();
        centerLayout.setLayout(new GridLayout(8,1));

        centerLayout.add(searchPanel);

        //proj name panel
        projNamePanel = new JPanel();
        projNamePanel.setLayout(new GridBagLayout());
        JLabel projNameLabel = new JLabel("Project Name: ");
        projNameField = new JTextField();
        projNameField.setPreferredSize(new Dimension(150,35));
        projNamePanel.add(projNameLabel);
        projNamePanel.add(projNameField);
        centerLayout.add(projNamePanel);

        //start date panel
        startDatePanel = new JPanel();
        startDatePanel.setLayout(new GridBagLayout());
        JLabel startDateLabel = new JLabel("Start Date: ");
        startDateField = new JTextField();
        startDateField.setPreferredSize(new Dimension(150,35));
        startDatePanel.add(startDateLabel);
        startDatePanel.add(startDateField);
        centerLayout.add(startDatePanel);

        //end date panel
        endDatePanel = new JPanel();
        endDatePanel.setLayout(new GridBagLayout());
        JLabel endDateLabel = new JLabel("End Date: ");
        endDateField = new JTextField();
        endDateField.setPreferredSize(new Dimension(150,35));
        endDatePanel.add(endDateLabel);
        endDatePanel.add(endDateField);
        centerLayout.add(endDatePanel);

        //story point panel
        pointPanel = new JPanel();
        pointPanel.setLayout(new GridBagLayout());
        JLabel pointLabel = new JLabel("Story Point Value: ");
        pointField = new JTextField();
        pointField.setPreferredSize(new Dimension(150,35));
        pointPanel.add(pointLabel);
        pointPanel.add(pointField);
        centerLayout.add(pointPanel);

        //scrum master panel
        smPanel = new JPanel();
        smPanel.setLayout(new GridBagLayout());
        JLabel smLabel = new JLabel("Scrum Master: ");
        Vector smArray = new Vector();
        model = new DefaultComboBoxModel(smArray);
        JComboBox<String> smComboBox = new JComboBox<>(model);
        smPanel.add(smLabel);
        smPanel.add(smComboBox);
        centerLayout.add(smPanel);

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
        centerLayout.add(durPanel);


        add(centerLayout,BorderLayout.CENTER);


        //button panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        JButton create = new JButton("Create");
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
    }

}
