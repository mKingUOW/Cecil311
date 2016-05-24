package com.scrumSystem.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Darryl on 12/05/2016.
 */
public class ProductOwnerView extends MemberView {

    private JFrame frame;
    private JPanel navigator;

    private ProductBacklogView productBacklogView;
    private SprintManagementView sprintManagementView;
    private ActiveProjectDetailsView activeProjectDetailsView;
    private MyDetailsView myDetailsView;

    private NavButton projectDetailsButton;
    private NavButton projectBacklogButton;
    private NavButton sprintButton;
    private NavButton myDetailsButton;
    private NavButton logOutButton;


    public ProductOwnerView(JFrame p, String uname){
        setUsername(uname);
        frame = p;

        //prepare();
    }

    public void prepare(){

        productBacklogView = new ProductBacklogView("ProductOwner",frame,this);
        sprintManagementView = new SprintManagementView(frame,this);
        activeProjectDetailsView = new ActiveProjectDetailsView(frame,this);
        myDetailsView = new MyDetailsView(frame,this);
        setCurrentView(this);

        setLayout(new BorderLayout());

          /*      HEADER PANEL (Primary header of window)      */
        final JPanel header = new JPanel();
        header.setPreferredSize(new Dimension(getWidth()-16,30));
        header.setBorder(BorderFactory.createRaisedBevelBorder());
        header.setBackground(Color.decode("#EBF0F2"));
        JLabel headerContent = new JLabel("Product Owner View");
        headerContent.setFont(new Font(headerContent.getFont().getName(),Font.BOLD,15));
        header.add(headerContent);

          /*      NAVIGATOR PANEL     */
        navigator = new JPanel();
        navigator.setLayout(new GridLayout(12,1));
        navigator.setBackground(Color.decode("#EBF0F2"));
        navigator.setBorder(BorderFactory.createRaisedBevelBorder());

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

        projectBacklogButton = new NavButton("Product Backlog",this);
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

        sprintButton = new NavButton("Sprint Management",this);
        sprintButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.remove(getCurrentView());
                frame.add(sprintManagementView,BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                setCurrentView(sprintManagementView);
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
        navigator.add(sprintButton);
        navigator.add(myDetailsButton);
        navigator.add(logOutButton);





        //add all panels to frame
        frame.add(header,BorderLayout.NORTH);
        frame.add(navigator,BorderLayout.WEST);
        frame.add(productBacklogView,BorderLayout.CENTER);

        setCurrentView(productBacklogView);
        //frame.setVisible(true);

    }

    public void showView(){
        frame.setVisible(true);
    }

    public void deselectAllNavigatorButtons(){
        projectDetailsButton.deSelect();
        projectBacklogButton.deSelect();
        sprintButton.deSelect();
        myDetailsButton.deSelect();
    }

}
