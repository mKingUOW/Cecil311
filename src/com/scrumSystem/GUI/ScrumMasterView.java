package com.scrumSystem.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Darryl on 24/05/2016.
 */
public class ScrumMasterView extends MemberView{
    private JFrame parentFrame;
    private JPanel navigator;

    //sys admin views
    private MemberView sysAdminView;
    private CreateNewUserView createNewUserView;
    private ModifyUserView modifyUserView;
    private CreateNewProjectView createNewProjectView;
    private ModifyProjectView modifyProjectView;

    //product owner views
    private ProductBacklogView productBacklogView;
    private SprintManagementView sprintManagementView;
    private ActiveProjectDetailsView activeProjectDetailsView;
    private MyDetailsView myDetailsView;

    //team member views
    private SprintBacklogView sprintBacklogView;
    private SprintBoardView sprintBoardView;
    private JPanel reportsView;

    //sys admin buttons
    private NavButton createNewProject;
    private NavButton modifyProject;
    private NavButton createNewUser;
    private NavButton modifyUser;
    private NavButton logOutButton;

    //product owner buttons
    private NavButton projectDetailsButton;
    private NavButton projectBacklogButton;
    private NavButton sprintButton;
    private NavButton myDetailsButton;

    //team member buttons
    private NavButton sprintBacklogButton;
    private NavButton sprintBoardButton;
    private NavButton reportsButton;

    public ScrumMasterView(JFrame p,String uname){
        setUsername(uname);
        parentFrame = p;
        sysAdminView = this;
        setCurrentView(this);
        createNewUserView = new CreateNewUserView(parentFrame,sysAdminView);
        modifyUserView = new ModifyUserView(parentFrame,sysAdminView);
        createNewProjectView = new CreateNewProjectView(parentFrame,sysAdminView);
        modifyProjectView = new ModifyProjectView(parentFrame,sysAdminView);
        productBacklogView = new ProductBacklogView("ProductOwner",parentFrame,this);
        sprintManagementView = new SprintManagementView(parentFrame,this);
        activeProjectDetailsView = new ActiveProjectDetailsView(parentFrame,this);
        myDetailsView = new MyDetailsView(parentFrame,this);
        sprintBacklogView = new SprintBacklogView(parentFrame,getCurrentView(),this);
        sprintBoardView = new SprintBoardView(parentFrame,getCurrentView(),this);
        reportsView = new SprintReviewView(parentFrame,getCurrentView());

        prepare();
    }

    public void prepare(){
        setLayout(new BorderLayout());

        /*      HEADER PANEL (Primary header of window)      */
        JPanel header = new JPanel();
        header.setPreferredSize(new Dimension(getWidth()-16,30));
        header.setBorder(BorderFactory.createRaisedBevelBorder());
        header.setBackground(Color.decode("#EBF0F2"));
        JLabel headerContent = new JLabel("Scrum Master View");
        headerContent.setFont(new Font(headerContent.getFont().getName(),Font.BOLD,15));
        header.add(headerContent);

          /*      NAVIGATOR PANEL     */
        navigator = new JPanel();
        navigator.setLayout(new GridLayout(12,1));
        navigator.setBackground(Color.decode("#EBF0F2"));
        navigator.setBorder(BorderFactory.createRaisedBevelBorder());

                        /* ------ System Admin functions --------- */

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

                                 /* ------ Product owner functions --------- */
        projectDetailsButton = new NavButton("Project Details",this);
        projectDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.remove(getCurrentView());
                parentFrame.add(activeProjectDetailsView,BorderLayout.CENTER);
                parentFrame.revalidate();
                parentFrame.repaint();
                setCurrentView(activeProjectDetailsView);
            }
        });

        projectBacklogButton = new NavButton("Product Backlog",this);
        projectBacklogButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                parentFrame.remove(getCurrentView());
                parentFrame.add(productBacklogView,BorderLayout.CENTER);
                parentFrame.revalidate();
                parentFrame.repaint();
                setCurrentView(productBacklogView);
            }
        });

        sprintButton = new NavButton("Sprint Management",this);
        sprintButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                parentFrame.remove(getCurrentView());
                parentFrame.add(sprintManagementView,BorderLayout.CENTER);
                parentFrame.revalidate();
                parentFrame.repaint();
                setCurrentView(sprintManagementView);
            }
        });

        myDetailsButton = new NavButton("My Details",this);
        myDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.remove(getCurrentView());
                parentFrame.add(myDetailsView,BorderLayout.CENTER);
                parentFrame.revalidate();
                parentFrame.repaint();
                setCurrentView(myDetailsView);
            }
        });

                                /* ------ Team Member functions --------- */

        sprintBacklogButton = new NavButton("Sprint Backlog",this);
        sprintBacklogButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                parentFrame.remove(getCurrentView());
                parentFrame.add(sprintBacklogView,BorderLayout.CENTER);
                parentFrame.revalidate();
                parentFrame.repaint();
                setCurrentView(sprintBacklogView);
            }
        });
        sprintBoardButton = new NavButton("Sprint Board",this);
        sprintBoardButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                parentFrame.remove(getCurrentView());
                parentFrame.add(sprintBoardView,BorderLayout.CENTER);
                parentFrame.revalidate();
                parentFrame.repaint();
                setCurrentView(sprintBoardView);
            }
        });
        reportsButton = new NavButton("Reports",this);
        reportsButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                parentFrame.remove(getCurrentView());
                parentFrame.add(reportsView,BorderLayout.CENTER);
                parentFrame.revalidate();
                parentFrame.repaint();
                setCurrentView(reportsView);
            }
        });



        //sys admin
        navigator.add(createNewProject);
        navigator.add(modifyProject);
        navigator.add(createNewUser);
        navigator.add(modifyUser);

        //product owner
        navigator.add(projectDetailsButton);
        navigator.add(projectBacklogButton);
        navigator.add(sprintButton);
        navigator.add(myDetailsButton);

        //team member
        navigator.add(sprintBacklogButton);
        navigator.add(sprintBoardButton);
        navigator.add(reportsButton);


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
        logOutButton.deSelect();
        projectDetailsButton.deSelect();
        projectBacklogButton.deSelect();
        sprintButton.deSelect();
        myDetailsButton.deSelect();
        sprintBacklogButton.deSelect();
        sprintBoardButton.deSelect();
        reportsButton.deSelect();

    }
}
