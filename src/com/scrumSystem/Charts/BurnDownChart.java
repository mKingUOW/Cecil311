package com.scrumSystem.Charts;

import javax.swing.*;

import com.scrumSystem.GUI.MemberView;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


/**
 * Created by peter on 26/05/2016.
 */
public class BurnDownChart extends JPanel {

    MemberView mv;
    private static final long serialVersionUID = 1L;
    private ChartPanel chartPanel;
    private MemberView parentPanel;

    public BurnDownChart(String chartTitle, MemberView pp) {

        parentPanel = pp;
        JFreeChart lineChart = ChartFactory.createLineChart(chartTitle, "Sprints", "Story Points", createDataset(), PlotOrientation.VERTICAL, true, true, false);
        chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        loadData();
        add(chartPanel);
    }

    private DefaultCategoryDataset createDataset( )
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        int sprints = 360/6;
//        for (int i = 0; i <= 6; i++){
//            dataset.addValue(360 - sprints, "", "" + i);
//        }

        dataset.addValue( 360 , "Task Estimates (Days)" , "0" );
        dataset.addValue( 270 , "Task Estimates (Days)" , "1" );
        dataset.addValue( 300 , "Task Estimates (Days)" , "2" );
        dataset.addValue( 250 , "Task Estimates (Days)" , "3" );
        dataset.addValue( 235 , "Task Estimates (Days)" , "4" );
        dataset.addValue( 91 , "Task Estimates (Days)" , "5" );
        dataset.addValue( 0 , "Task Estimates (Days)" , "6" );
        return dataset;
    }

    public void loadData(){
        System.out.println(parentPanel.sc.getTotalStoryPointsForSprint(3));
    }
}
