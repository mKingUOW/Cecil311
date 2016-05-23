package com.scrumSystem.GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Darryl on 6/05/2016.
 */
public class TeamMemberView extends MemberView {

    private JFrame frame;
    private JPanel navigator;
    private ProductBacklogView productBacklogView;
    private SprintBacklogView sprintBacklogView;
    private ActiveProjectDetailsView activeProjectDetailsView;
    private SprintBoardView sprintBoardView;
    private JPanel reportsView;
    private MyDetailsView myDetailsView;


    private JPanel header;
    private JLabel headerContent;

    private NavButton projectDetailsButton;
    private NavButton projectBacklogButton;
    private NavButton sprintBacklogButton;
    private NavButton sprintBoardButton;
    private NavButton reportsButton;
    private NavButton myDetailsButton;
    private NavButton logOutButton;

    public TeamMemberView(JFrame  f,String uname){
        setUsername(uname);
        frame = f;
        prepare();
    }

    private void prepare(){
        /*
        frame = new JFrame("Team Member View");
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(new Dimension(1500,720));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        */

        /*      NAVIGATOR PANEL     */
        navigator = new JPanel();
        navigator.setLayout(new GridLayout(12,1));
        navigator.setBackground(Color.decode("#EBF0F2"));
        navigator.setBorder(BorderFactory.createRaisedBevelBorder());

        //navigator buttons for changing views
        projectDetailsButton = new NavButton("Project Details",this);
        projectDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(getCurrentView());
                frame.add(activeProjectDetailsView,BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                setCurrentView(activeProjectDetailsView);
            }
        });
        projectBacklogButton = new NavButton("Project Backlog",this);
        projectBacklogButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.remove(getCurrentView());
                frame.add(productBacklogView,BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                setCurrentView(productBacklogView);
            }
        });

        sprintBacklogButton = new NavButton("Sprint Backlog",this);
        sprintBacklogButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.remove(getCurrentView());
                frame.add(sprintBacklogView,BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                setCurrentView(sprintBacklogView);
            }
        });
        sprintBoardButton = new NavButton("Sprint Board",this);
        sprintBoardButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.remove(getCurrentView());
                frame.add(sprintBoardView,BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                setCurrentView(sprintBoardView);
            }
        });
        reportsButton = new NavButton("Reports",this);
        reportsButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.remove(getCurrentView());
                frame.add(reportsView,BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                setCurrentView(reportsView);
            }
        });

        myDetailsButton = new NavButton("My Details",this);
        myDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(getCurrentView());
                frame.add(myDetailsView,BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                setCurrentView(myDetailsView);
            }
        });
        logOutButton = new NavButton("Log Out", this);
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(frame,"Are you sure you wish to log out? ","Log Out",JOptionPane.OK_CANCEL_OPTION);
                if(res == JOptionPane.YES_OPTION){
                    frame.remove(getCurrentView());
                    frame.remove(navigator);
                    frame.remove(header);
                    frame.add(new LoginView(frame));
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        //add buttons to navigator panel
        navigator.add(projectDetailsButton);
        navigator.add(projectBacklogButton);
        navigator.add(sprintBacklogButton);
        navigator.add(sprintBoardButton);
        navigator.add(reportsButton);
        navigator.add(myDetailsButton);
        navigator.add(logOutButton);

          /*      HEADER PANEL (Primary header of window)      */
        header = new JPanel();
        header.setPreferredSize(new Dimension(getWidth()-16,30));
        header.setBorder(BorderFactory.createRaisedBevelBorder());
        header.setBackground(Color.decode("#EBF0F2"));
        headerContent = new JLabel("Team Member View");
        headerContent.setFont(new Font(headerContent.getFont().getName(),Font.BOLD,15));
        header.add(headerContent);

        /*  initialise various accessable views   */
        activeProjectDetailsView = new ActiveProjectDetailsView(frame,this);
        productBacklogView = new ProductBacklogView("TeamMember",frame,this);
        sprintBacklogView = new SprintBacklogView(frame,getCurrentView(),this);
        sprintBoardView = new SprintBoardView(frame,getCurrentView(),this);
        reportsView = new SprintReviewView(frame,getCurrentView());
        myDetailsView = new MyDetailsView(frame,this);

        //set projectBacklogView as default landing page
        setCurrentView(productBacklogView);
        projectBacklogButton.select();

        //add all panels to frame
        frame.add(header,BorderLayout.NORTH);
        frame.add(navigator,BorderLayout.WEST);
        frame.add(getCurrentView(),BorderLayout.CENTER);

        //select product backlog view by default

        //frame.setVisible(true);
    }

    public void showView(){
        frame.setVisible(true);
    }

    public void deselectAllNavigatorButtons(){
        projectDetailsButton.deSelect();
        projectBacklogButton.deSelect();
        sprintBacklogButton.deSelect();
        sprintBoardButton.deSelect();
        reportsButton.deSelect();
        myDetailsButton.deSelect();
        //seven.deSelect();
        //eight.deSelect();
    }

}


class NavButton extends JButton{

    private Boolean isSelected = false;
    private MemberView parentView = null;

    public NavButton(String text, MemberView f){
        parentView = f;
        this.setText(text);
        //setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        Border margin = new EmptyBorder(0,10,0,10);
        setBorder(new CompoundBorder(BorderFactory.createRaisedBevelBorder(),margin));

        this.setFocusPainted(false);

        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(!isSelected) {
                    setBackground(Color.decode("#90C3D4"));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if(!isSelected) {
                    deSelect();
                }
            }
        });

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                select();
            }
        });

    }

    public void deSelect(){
        isSelected = false;
        setBackground(new JButton().getBackground());
    }

    public void select(){
        parentView.deselectAllNavigatorButtons();
        isSelected = true;
        setBackground(Color.lightGray);
    }

}


