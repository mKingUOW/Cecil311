package com.scrumSystem.GUI;

import com.scrumSystem.Charts.BurnDownChart;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Darryl on 26/05/2016.
 */
public class BurnDownChartView extends JPanel {

    private BurnDownChart burnDownChart;
    private JFrame parentFrame;
    private MemberView parentPanel;

    public BurnDownChartView(JFrame f, MemberView pp){
        parentFrame = f;
        parentPanel = pp;

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
    }
}
