package com.scrumSystem.GUI;

import com.scrumSystem.Charts.BurnDownChart;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Darryl on 26/05/2016.
 */
public class BurnDownChartView extends JPanel {

    private BurnDownChart burnDownChart;
    private JFrame parentFrame;
    private MemberView parentPanel;
    private JPanel retView;

    public BurnDownChartView(JFrame f, MemberView pp, JPanel ret){
        parentFrame = f;
        parentPanel = pp;
        retView = ret;

//        prepare();
    }

    public void prepare(){

        setLayout(new BorderLayout());

        //header
        JLabel header = new JLabel("Burn Down Chart", SwingConstants.CENTER);
        add(header, BorderLayout.NORTH);

        // Panel
        burnDownChart = new BurnDownChart("Current Project" + " " + parentPanel.getActiveProj(), parentPanel);
        burnDownChart.setVisible(true);
        add(burnDownChart, BorderLayout.CENTER);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.remove(parentPanel.getCurrentView());
                parentFrame.add(retView,BorderLayout.CENTER);
                parentFrame.revalidate();
                parentFrame.repaint();
                parentPanel.setCurrentView(retView);
            }
        });
        exitButton.setPreferredSize(new Dimension(75,35));
        JPanel buttonLayout = new JPanel();
        buttonLayout.setLayout(new GridBagLayout());
        buttonLayout.add(exitButton);

        add(buttonLayout,BorderLayout.SOUTH);
    }
}
