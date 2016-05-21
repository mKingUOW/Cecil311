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

    public SystemAdminView(JFrame p){
        parentFrame = p;
        sysAdminView = this;
        setCurrentView(this);
        createNewUserView = new CreateNewUserView(parentFrame,sysAdminView);
        modifyUserView = new ModifyUserView(parentFrame,sysAdminView);
        createNewProjectView = new CreateNewProjectView(parentFrame,sysAdminView);
        modifyProjectView = new ModifyProjectView(parentFrame,sysAdminView);

        prepare();
    }

    public void prepare(){

        setLayout(new BorderLayout());

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
                parentFrame.add(modifyUserView,BorderLayout.CENTER);
                parentFrame.revalidate();
                parentFrame.repaint();
                setCurrentView(modifyUserView);
            }
        });

        navigator.add(createNewProject);
        navigator.add(modifyProject);
        navigator.add(createNewUser);
        navigator.add(modifyUser);

        /*      HEADER PANEL (Primary header of window)      */
        JPanel header = new JPanel();
        header.setPreferredSize(new Dimension(getWidth()-16,30));
        header.setBorder(BorderFactory.createRaisedBevelBorder());
        header.setBackground(Color.decode("#EBF0F2"));
        JLabel headerContent = new JLabel("System Admin View");
        headerContent.setFont(new Font(headerContent.getFont().getName(),Font.BOLD,15));
        header.add(headerContent);


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
