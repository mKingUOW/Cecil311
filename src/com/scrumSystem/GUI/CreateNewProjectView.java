package com.scrumSystem.GUI;

import com.scrumSystem.Helpers.ProjectDetailsHelper;
import com.scrumSystem.project.ProjectDetails;
import com.scrumSystem.project.SessionController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Created by Darryl on 21-May-16.
 */
public class CreateNewProjectView extends JPanel {

    private JFrame parentFrame;
    private MemberView parentPanel;

    private JTextField projNameField;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField pointField;
    private JTextField durField;

    public CreateNewProjectView(JFrame p, MemberView pp){
        parentFrame = p;
        parentPanel = pp;

        prepare();
    }

    public void prepare(){
        setLayout(new BorderLayout());

        //header panel
        JLabel header = new JLabel("Create New Project",SwingConstants.CENTER);
        header.setPreferredSize(new Dimension(1000,100));
        add(header,BorderLayout.NORTH);

        //center panel
        JPanel centerLayout = new JPanel();
        centerLayout.setLayout(new GridLayout(7,1));

        //proj name panel
        JPanel projNamePanel = new JPanel();
        projNamePanel.setLayout(new GridBagLayout());
        JLabel projNameLabel = new JLabel("Project Name: ");
        projNameField = new JTextField();
        projNameField.setPreferredSize(new Dimension(150,35));
        projNamePanel.add(projNameLabel);
        projNamePanel.add(projNameField);
        centerLayout.add(projNamePanel);

        //start date panel
        JPanel startDatePanel = new JPanel();
        startDatePanel.setLayout(new GridBagLayout());
        JLabel startDateLabel = new JLabel("Start Date: ");
        startDateField = new JTextField();
        startDateField.setPreferredSize(new Dimension(150,35));
        startDatePanel.add(startDateLabel);
        startDatePanel.add(startDateField);
        centerLayout.add(startDatePanel);

        //end date panel
        JPanel endDatePanel = new JPanel();
        endDatePanel.setLayout(new GridBagLayout());
        JLabel endDateLabel = new JLabel("End Date: ");
        endDateField = new JTextField();
        endDateField.setPreferredSize(new Dimension(150,35));
        endDatePanel.add(endDateLabel);
        endDatePanel.add(endDateField);
        centerLayout.add(endDatePanel);

        //story point panel
        JPanel pointPanel = new JPanel();
        pointPanel.setLayout(new GridBagLayout());
        JLabel pointLabel = new JLabel("Story Point Value: ");
        pointField = new JTextField();
        pointField.setPreferredSize(new Dimension(150,35));
        pointPanel.add(pointLabel);
        pointPanel.add(pointField);
        centerLayout.add(pointPanel);

        //scrum master panel
        JPanel smPanel = new JPanel();
        smPanel.setLayout(new GridBagLayout());
        JLabel smLabel = new JLabel("Scrum Master: ");

        final Vector smArray = new Vector(parentPanel.sc.getAvailableSMs());
        final DefaultComboBoxModel<String> model = new DefaultComboBoxModel(smArray);
        final JComboBox<String> smComboBox = new JComboBox<>(model);
        smPanel.add(smLabel);
        smPanel.add(smComboBox);
        centerLayout.add(smPanel);

        //sprint duration panel
        JPanel durPanel = new JPanel();
        durPanel.setLayout(new GridBagLayout());
        JLabel durLabel = new JLabel("Sprint Duration (weeks): ");
        durField = new JTextField();
        durField.setPreferredSize(new Dimension(150,35));
        durPanel.add(durLabel);
        durPanel.add(durField);
        centerLayout.add(durPanel);


        add(centerLayout,BorderLayout.CENTER);


        //button panel
        JPanel buttonPanel = new JPanel();
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

                    for(int i = 0; i<parentPanel.sc.getAvailableSMs().size(); i++){
                        model.addElement(parentPanel.sc.getAvailableSMs().get(i));
                    }
                }
            }
        });

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //save user to db
                //create new project
                parentPanel.sc.createProject(projNameField.getText(),(String)smComboBox.getSelectedItem());
                ProjectDetails pd = parentPanel.sc.getProjectDetails(projNameField.getText());

                //setup other details of project (if set)
                if(projNameField.getText().equals("")){
                    //alert error
                }
                else{
                    pd.setName(projNameField.getText());
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

                pd.setScrumMaster((String)smComboBox.getSelectedItem());

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

                pd.setScrumMaster((String)smComboBox.getSelectedItem());


                System.out.println(pd.toCSV());
                parentPanel.sc.saveProject(pd);




                //show alert
                JOptionPane.showMessageDialog(null,"Project Saved");
                projNameField.setText("");
                startDateField.setText("");
                endDateField.setText("");
                pointField.setText("");
                durField.setText("");
                model.removeAllElements();

                for(int i = 0; i<parentPanel.sc.getAvailableSMs().size(); i++){
                    model.addElement(parentPanel.sc.getAvailableSMs().get(i));
                }
            }
        });

        buttonPanel.add(create);
        buttonPanel.add(cancel);
        add(buttonPanel,BorderLayout.SOUTH);


    }

}
