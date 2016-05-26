package com.scrumSystem.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Darryl on 27/05/2016.
 */
public class ReportsView extends JPanel {

    public JFrame parentFrame;
    public MemberView parentPanel;
    public JPanel currView;

    public ReportsView(JFrame f, MemberView m){
        parentFrame = f;
        parentPanel = m;
        currView = this;
        prepare();
    }

    public void prepare(){
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Reports", SwingConstants.CENTER);

        JPanel centerLayout = new JPanel();
        centerLayout.setLayout(new GridLayout(1,2));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        JButton sRev = new JButton("Sprint Review");
        sRev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SprintReviewView sprintReviewView = new SprintReviewView(parentFrame,currView,parentPanel);
                parentFrame.remove(parentPanel.getCurrentView());
                sprintReviewView.prepare();
                parentFrame.add(sprintReviewView);
                parentFrame.revalidate();
                parentFrame.repaint();
                parentPanel.setCurrentView(sprintReviewView);
            }
        });
        sRev.setPreferredSize(new Dimension(300,300));
        leftPanel.add(sRev);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        JButton bdChart = new JButton("Burn Down Chart");
        bdChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BurnDownChartView burnDownChartView = new BurnDownChartView(parentFrame,parentPanel,currView);
                parentFrame.remove(parentPanel.getCurrentView());
                burnDownChartView.prepare();
                parentFrame.add(burnDownChartView);
                parentFrame.revalidate();
                parentFrame.repaint();
                parentPanel.setCurrentView(burnDownChartView);

            }
        });
        bdChart.setPreferredSize(new Dimension(300,300));
        rightPanel.add(bdChart);

        centerLayout.add(leftPanel);
        centerLayout.add(rightPanel);

        add(header,BorderLayout.NORTH);
        add(centerLayout,BorderLayout.CENTER);
    }

}
