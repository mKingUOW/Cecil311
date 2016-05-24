package com.scrumSystem.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Darryl on 24/05/2016.
 */
public class NoProjectView extends JPanel {
    private JFrame frame;
    private JPanel currentView;

    public NoProjectView(JFrame f){
        frame = f;
        currentView = this;
        prepare();
    }

    public void prepare(){
        setLayout(new BorderLayout());
        final JLabel header = new JLabel("You are not assigned to a project, contact you're scrum master or system admin.", SwingConstants.CENTER);
        add(header,BorderLayout.NORTH);

        JButton logOutButton = new JButton("Log Out");
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(frame,"Are you sure you wish to log out? ","Log Out",JOptionPane.OK_CANCEL_OPTION);
                if(res == JOptionPane.YES_OPTION){
                    frame.remove(currentView);
                    frame.add(new LoginView(frame));
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });
        logOutButton.setPreferredSize(new Dimension(100,35));
        JPanel buttonLayout = new JPanel();
        buttonLayout.setLayout(new GridBagLayout());
        buttonLayout.add(logOutButton);

        add(buttonLayout,BorderLayout.SOUTH);

        frame.add(this);
    }

    public void showView(){
        setVisible(true);
    }
}
