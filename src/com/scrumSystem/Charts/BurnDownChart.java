package com.scrumSystem.Charts;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


/**
 * Created by peter on 26/05/2016.
 */
public class BurnDownChart extends JFrame {

    private static final long serialVersionUID = 1L;

    public BurnDownChart(String applicationTitle, String chartTitle) {

        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Iteration Timeline (Days)", "Summary of Task Estimates (Days)",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset( )
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue( 15 , "" , "1970" );
        return dataset;
    }
}
