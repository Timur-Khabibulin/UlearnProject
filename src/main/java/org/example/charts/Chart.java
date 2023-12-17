package org.example.charts;

import lombok.AllArgsConstructor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;

import javax.swing.*;
import java.awt.*;

@AllArgsConstructor
public class Chart {

    private CategoryDataset categoryDataset;
    private String title;
    private String xLabel;
    private String yLabel;

    public void run() {
        var chartPanel = createChartPanel();
        var frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private ChartPanel createChartPanel() {
        var chart = createBarChart(
                categoryDataset,
                title,
                xLabel,
                yLabel
        );
        var chartPanel = new ChartPanel(chart);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 370));
        return chartPanel;
    }

    private JFreeChart createBarChart(
            CategoryDataset dataset,
            String title,
            String xLabel,
            String yLabel
    ) {
        return setUpChart(
                ChartFactory.createBarChart(
                        title,
                        xLabel,
                        yLabel,
                        dataset
                )
        );
    }

    private JFreeChart setUpChart(JFreeChart chart) {
        chart.setBackgroundPaint(Color.WHITE);
        var plot = (CategoryPlot) chart.getPlot();

        var rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        var renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        chart.getLegend().setFrame(BlockBorder.NONE);
        return chart;
    }
}
