package com.scrumSystem.GUI;

import com.scrumSystem.project.ProjectDetails;
import com.scrumSystem.project.SessionController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Darryl on 18-May-16.
 */
public class LoginView extends JPanel {

    private JFrame parentFrame;
    private SessionController sc;
    private JLabel feedback;
    private JPanel currentView;

    public LoginView(JFrame p){
        parentFrame = p;
        sc = new SessionController();
        currentView = this;
        prepare();
    }

    public void prepare(){
        JLabel header = new JLabel("Scrum Tool", SwingConstants.CENTER);
        header.setPreferredSize(new Dimension(1000,250));
        //add(header, BorderLayout.NORTH);

        JLabel uname = new JLabel("Username: ", SwingConstants.CENTER);
        JLabel pwd = new JLabel("Password: ", SwingConstants.CENTER);

        //center layout
        //JPanel centerLayout = new JPanel();
        //centerLayout.setLayout(new GridLayout(2,1));
        final JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(150,35));
        final JTextField pwdField = new JTextField();
        pwdField.setPreferredSize(new Dimension(150,35));
        add(uname,BorderLayout.CENTER);
        add(usernameField,BorderLayout.CENTER);
        add(pwd,BorderLayout.CENTER);
        add(pwdField,BorderLayout.CENTER);

        JButton login = new JButton("Login");
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sc.attemptUserLogin(usernameField.getText(),pwdField.getText()) == true) {


                    if (sc.getUserRoleType().equals("SM")) {
                        //scrum master
                        if(sc.getStartingState().equals("project")){
                            //proj already setup
                            parentFrame.remove(currentView);
                            ScrumMasterView scrumMasterView = new ScrumMasterView(parentFrame, usernameField.getText());
                            scrumMasterView.setUserEntity(sc.getUsersDetails());
                            scrumMasterView.setSessionController(sc);
                            scrumMasterView.prepare();
                            scrumMasterView.showView();
                            parentFrame.revalidate();
                            parentFrame.repaint();
                        }
                        else if(sc.getStartingState().equals("noProject")){
                            //user not assigned to a project

                            parentFrame.remove(currentView);
                            NoProjectView noProjectView = new NoProjectView(parentFrame);
                            noProjectView.showView();
                            parentFrame.revalidate();
                            parentFrame.repaint();

                            /*
                            //skip this atm
                            parentFrame.remove(currentView);
                            ScrumMasterView scrumMasterView = new ScrumMasterView(parentFrame, usernameField.getText());
                            scrumMasterView.setUserEntity(sc.getUsersDetails());
                            scrumMasterView.setSessionController(sc);
                            scrumMasterView.prepare();
                            scrumMasterView.showView();
                            parentFrame.revalidate();
                            parentFrame.repaint();
                            */
                        }
                        else if(sc.getStartingState().equals("setup")){
                            parentFrame.remove(currentView);
                            ScrumMasterView scrumMasterView = new ScrumMasterView(parentFrame, usernameField.getText());
                            scrumMasterView.setUserEntity(sc.getUsersDetails());
                            scrumMasterView.setSessionController(sc);
                            scrumMasterView.prepare();
                            scrumMasterView.showView();
                            parentFrame.revalidate();
                            parentFrame.repaint();
                        }
                        else{
                            //error
                            System.out.println("error in starting state");

                        }


                    } else if (sc.getUserRoleType().equals("TM")) {
                        //team member
                        if(sc.getStartingState().equals("project")){
                            parentFrame.remove(currentView);
                            TeamMemberView teamMemberView = new TeamMemberView(parentFrame,usernameField.getText());
                            teamMemberView.setUserEntity(sc.getUsersDetails());
                            teamMemberView.setSessionController(sc);
                            teamMemberView.prepare();
                            teamMemberView.showView();
                            parentFrame.revalidate();
                            parentFrame.repaint();
                        }
                        else if(sc.getStartingState().equals("noProject")){
                            //user not assigned to a project
                            System.out.println("no proj");

                            parentFrame.remove(currentView);
                            NoProjectView noProjectView = new NoProjectView(parentFrame);
                            noProjectView.showView();
                            parentFrame.revalidate();
                            parentFrame.repaint();


                            /*
                            //skip this atm
                            parentFrame.remove(currentView);
                            TeamMemberView teamMemberView = new TeamMemberView(parentFrame,usernameField.getText());
                            teamMemberView.setUserEntity(sc.getUsersDetails());
                            teamMemberView.setSessionController(sc);
                            teamMemberView.prepare();
                            teamMemberView.showView();
                            parentFrame.revalidate();
                            parentFrame.repaint();
                            */
                        }
                        else{
                            System.out.println("error in starting state");
                        }




                    } else if (sc.getUserRoleType().equals("PO")) {
                        //product owner
                        if(sc.getStartingState().equals("project")){
                            parentFrame.remove(currentView);
                            ProductOwnerView productOwnerView = new ProductOwnerView(parentFrame,usernameField.getText());
                            productOwnerView.setUserEntity(sc.getUsersDetails());
                            productOwnerView.setSessionController(sc);
                            productOwnerView.prepare();
                            productOwnerView.showView();
                            parentFrame.revalidate();
                            parentFrame.repaint();
                        }
                        else if(sc.getStartingState().equals("noProject")){
                            //user not assigned to a project

                            parentFrame.remove(currentView);
                            NoProjectView noProjectView = new NoProjectView(parentFrame);
                            noProjectView.showView();
                            parentFrame.revalidate();
                            parentFrame.repaint();

                            /*
                            //skip this atm
                            parentFrame.remove(currentView);
                            ProductOwnerView productOwnerView = new ProductOwnerView(parentFrame,usernameField.getText());
                            productOwnerView.setUserEntity(sc.getUsersDetails());
                            productOwnerView.setSessionController(sc);
                            productOwnerView.prepare();
                            productOwnerView.showView();
                            parentFrame.revalidate();
                            parentFrame.repaint();
                            */
                        }
                        else{
                            System.out.println("error in starting state");
                        }




                    } else if (sc.getUserRoleType().equals("SA")) {
                        //system admin

                        System.out.println("System admin logged in");
                        parentFrame.remove(currentView);
                        SystemAdminView systemAdminView = new SystemAdminView(parentFrame,usernameField.getText());
                        systemAdminView.setUserEntity(sc.getUsersDetails());
                        systemAdminView.setSessionController(sc);
                        systemAdminView.prepare();
                        systemAdminView.showView();
                        parentFrame.revalidate();
                        parentFrame.repaint();
                    } else {
                        feedback.setText("error");
                    }
                }
                else{
                    feedback.setText("error");
                }
            }
        });
        add(login,BorderLayout.CENTER);

        //south layout
        feedback = new JLabel("", SwingConstants.CENTER);
        feedback.setPreferredSize(new Dimension(100,250));
        add(feedback,BorderLayout.SOUTH);

       // add(centerLayout,BorderLayout.CENTER);

        parentFrame.add(this);

    }

    public void showView(){
        parentFrame.setVisible(true);
    }
}
