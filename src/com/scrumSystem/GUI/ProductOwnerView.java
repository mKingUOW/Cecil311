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
    private JPanel currentView;

    private ProductBacklogView productBacklogView;

    private NavButton projectDetailsButton;
    private NavButton projectBacklogButton;
    private NavButton sprintBacklogButton;
    private NavButton myDetailsButton;


    public ProductOwnerView(JFrame p){
        frame = p;
        currentView = null;
        productBacklogView = new ProductBacklogView("ProductOwner",frame,this);
        prepare();
    }

    public void prepare(){
        setLayout(new BorderLayout());

          /*      NAVIGATOR PANEL     */
        navigator = new JPanel();
        navigator.setLayout(new GridLayout(12,1));
        navigator.setBackground(Color.decode("#EBF0F2"));
        navigator.setBorder(BorderFactory.createRaisedBevelBorder());

        projectDetailsButton = new NavButton("Project Details",this);
        projectBacklogButton = new NavButton("Project Backlog",this);
        projectBacklogButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.remove(currentView);
                frame.add(productBacklogView,BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                currentView = productBacklogView;
            }
        });
        sprintBacklogButton = new NavButton("Sprint Backlog",this);
        myDetailsButton = new NavButton("My Details",this);

        //add buttons to navigator panel
        navigator.add(projectDetailsButton);
        navigator.add(projectBacklogButton);
        navigator.add(sprintBacklogButton);
        navigator.add(myDetailsButton);

         /*      HEADER PANEL (Primary header of window)      */
        JPanel header = new JPanel();
        header.setPreferredSize(new Dimension(getWidth()-16,30));
        header.setBorder(BorderFactory.createRaisedBevelBorder());
        header.setBackground(Color.decode("#EBF0F2"));
        JLabel headerContent = new JLabel("Product Owner View");
        headerContent.setFont(new Font(headerContent.getFont().getName(),Font.BOLD,15));
        header.add(headerContent);

        //set productbacklogveiw as landing page.


        //add all panels to frame
        frame.add(header,BorderLayout.NORTH);
        frame.add(navigator,BorderLayout.WEST);
        frame.add(productBacklogView,BorderLayout.CENTER);

        currentView = productBacklogView;
        //frame.setVisible(true);

    }

    public void showView(){
        frame.setVisible(true);
    }

    public void deselectAllNavigatorButtons(){
        projectDetailsButton.deSelect();
        projectBacklogButton.deSelect();
        sprintBacklogButton.deSelect();
        myDetailsButton.deSelect();
    }
}
