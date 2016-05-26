package com.scrumSystem.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Darryl on 19-May-16.
 */
public class SystemAdminView extends MemberView{

    private JFrame parentFrame;
    private JPanel navigator;
    private MemberView sysAdminView;
    private CreateNewUserView createNewUserView;
    private ModifyUserView modifyUserView;
    private CreateNewProjectView createNewProjectView;
    private ModifyProjectView modifyProjectView;

    private NavButton createNewProject;
    private NavButton modifyProject;
    private NavButton createNewUser;
    private NavButton modifyUser;
    private NavButton logOutButton;

    public SystemAdminView(JFrame p,String uname){
        setUsername(uname);
        parentFrame = p;

       // prepare();
    }

    public void prepare(){

        sysAdminView = this;
        setCurrentView(this);
        createNewUserView = new CreateNewUserView(parentFrame,sysAdminView);
        modifyUserView = new ModifyUserView(parentFrame,sysAdminView);
        createNewProjectView = new CreateNewProjectView(parentFrame,sysAdminView);
        modifyProjectView = new ModifyProjectView(parentFrame,sysAdminView);

        setLayout(new BorderLayout());

        /*      HEADER PANEL (Primary header of window)      */
        final JPanel header = new JPanel();
        header.setPreferredSize(new Dimension(getWidth()-16,30));
        header.setBorder(BorderFactory.createRaisedBevelBorder());
        header.setBackground(Color.decode("#EBF0F2"));
        JLabel headerContent = new JLabel("System Admin View");
        headerContent.setFont(new Font(headerContent.getFont().getName(),Font.BOLD,15));
        header.add(headerContent);

          /*      NAVIGATOR PANEL     */
        navigator = new JPanel();
        navigator.setLayout(new GridLayout(12,1));
        navigator.setBackground(Color.decode("#EBF0F2"));
        navigator.setBorder(BorderFactory.createRaisedBevelBorder());

        createNewProject = new NavButton("Create New Project",this);
        createNewProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.remove(getCurrentView());
                createNewProjectView.removeAll();
                createNewProjectView.prepare();
                parentFrame.add(createNewProjectView,BorderLayout.CENTER);
                parentFrame.revalidate();
                parentFrame.repaint();
                setCurrentView(createNewProjectView);
            }
        });

        modifyProject = new NavButton("Modify Existing Project",this);
        modifyProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.remove(getCurrentView());
                modifyProjectView.removeAll();
                modifyProjectView.prepare();
                parentFrame.add(modifyProjectView,BorderLayout.CENTER);
                parentFrame.revalidate();
                parentFrame.repaint();
                setCurrentView(modifyProjectView);
            }
        });

        createNewUser = new NavButton("Create New User", this);
        createNewUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.remove(getCurrentView());
                createNewUserView.removeAll();
                createNewUserView.prepare();
                parentFrame.add(createNewUserView,BorderLayout.CENTER);
                parentFrame.revalidate();
                parentFrame.repaint();
                setCurrentView(createNewUserView);
            }
        });
        modifyUser = new NavButton("Modify Existing User",this);
        modifyUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.remove(getCurrentView());
                modifyUserView.removeAll();
                modifyUserView.prepare();
                parentFrame.add(modifyUserView,BorderLayout.CENTER);
                parentFrame.revalidate();
                parentFrame.repaint();
                setCurrentView(modifyUserView);
            }
        });

        logOutButton = new NavButton("Log Out", this);
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(parentFrame,"Are you sure you wish to log out? ","Log Out",JOptionPane.OK_CANCEL_OPTION);
                if(res == JOptionPane.YES_OPTION){
                    parentFrame.remove(getCurrentView());
                    parentFrame.remove(navigator);
                    parentFrame.remove(header);
                    parentFrame.add(new LoginView(parentFrame));
                    parentFrame.revalidate();
                    parentFrame.repaint();
                }
            }
        });



        navigator.add(createNewProject);
        navigator.add(modifyProject);
        navigator.add(createNewUser);
        navigator.add(modifyUser);
        navigator.add(logOutButton);


        parentFrame.add(header,BorderLayout.NORTH);
        parentFrame.add(navigator, BorderLayout.WEST);

        //landing page
        parentFrame.add(createNewUserView,BorderLayout.CENTER); //set create new user as landing page
        setCurrentView(createNewUserView);
        createNewUser.select();


    }

    public void showView(){
        parentFrame.setVisible(true);
    }

    public void deselectAllNavigatorButtons(){
        createNewProject.deSelect();
        modifyProject.deSelect();
        createNewUser.deSelect();
        modifyUser.deSelect();
    }
}
