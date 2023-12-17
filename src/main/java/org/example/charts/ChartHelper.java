package org.example.charts;

import org.example.db.entities.Student;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class ChartHelper {
    public static List<Chart> createCitiesCharts(
            Map<String, Long> data
    ) {
        var maxDataset = new DefaultCategoryDataset();
        var otherDataset = new DefaultCategoryDataset();
        var sorted = data.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .toList();

        sorted.subList(sorted.size() - 4, sorted.size()).forEach(x -> maxDataset.addValue(x.getValue(), x.getKey(), ""));
        sorted.subList(0, sorted.size() - 4).forEach(x -> otherDataset.addValue(x.getValue(), x.getKey(), ""));

        return List.of(
                new Chart(
                        maxDataset,
                        "Города, откуда больше всего студентов",
                        "Города",
                        "Кол-во"
                ),
                new Chart(
                        otherDataset,
                        "Остальные города студентов",
                        "Города",
                        "Кол-во"
                )
        );
    }

    public static Chart createSimpleChart(Map<String, Long> data, String title, String xLabel, String yLabel) {
        var dataset = new DefaultCategoryDataset();

        data.forEach((key, value) -> dataset.addValue(value, key, ""));

        return new Chart(
                dataset,
                title,
                xLabel,
                yLabel
        );
    }


    public static void createStudentsTotalScoreChart(Map<Student, Integer> data) {
        var series = new XYSeries("Chart");
        var count = 0;
        for (var entry : data.entrySet()) {
            series.add(count, entry.getValue());
            count++;
        }

        var dataset = new XYSeriesCollection(series);
        var chart = ChartFactory.createXYLineChart(
                "Успеваемость студентов",
                "студент",
                "балл за курс",
                dataset
        );

        var chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 370));

        var frame = new JFrame("'s Performance");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
