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
//        loadData();
        add(chartPanel);
    }

    private DefaultCategoryDataset createDataset( )
    {
        int numberOfSprints = parentPanel.sc.getCurrentSprint();
        System.out.println("numOfSprints :" + numberOfSprints);
//        parentPanel.sc.getCompletedFromSprint(1);
        int totalStoryPoints = parentPanel.sc.getTotalSprintPointsInProj(parentPanel.getActiveProj());
//        System.out.println("Number of Sprints " + numberOfSprints);

        System.out.println("Total Story Points " + totalStoryPoints);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i <= numberOfSprints; i++){
            if(i > 0){
                int completedSprintPoints = parentPanel.sc.getCompletedPointFromSprint(parentPanel.getActiveProj(),i);
                totalStoryPoints = totalStoryPoints - completedSprintPoints;
                System.out.println("curr: " + totalStoryPoints);
                if (totalStoryPoints >= 0) {
                    dataset.addValue(totalStoryPoints, "Points", "" + i);
                }
            }
            else{
                dataset.addValue(totalStoryPoints, "Points", "" + i);
            }
        }

//        dataset.addValue( 360 , "Task Estimates (Days)" , "0" );
//        dataset.addValue( 270 , "Task Estimates (Days)" , "1" );
//        dataset.addValue( 300 , "Task Estimates (Days)" , "2" );
//        dataset.addValue( 250 , "Task Estimates (Days)" , "3" );
//        dataset.addValue( 235 , "Task Estimates (Days)" , "4" );
//        dataset.addValue( 91 , "Task Estimates (Days)" , "5" );
//        dataset.addValue( 0 , "Task Estimates (Days)" , "6" );
        return dataset;
    }

    public void loadData(){
        System.out.println(parentPanel.sc.getTotalStoryPointsForSprint(3));
    }
}
