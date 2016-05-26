package com.scrumSystem.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Darryl on 26/05/2016.
 */
public class BurnDownChartView extends JPanel {

    private JFrame parentFrame;
    private MemberView parentPanel;

    public BurnDownChartView(JFrame f, MemberView pp){
        parentFrame = f;
        parentPanel = pp;

        prepare();
    }

    public void prepare(){
        setLayout(new BorderLayout());

        //header
        JLabel header = new JLabel("Burn Down Chart", SwingConstants.CENTER);
        add(header, BorderLayout.NORTH);

        //center
        //add chart to BorderLayout.CETER
    }
}
