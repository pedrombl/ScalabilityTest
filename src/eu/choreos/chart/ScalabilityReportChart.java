package eu.choreos.chart;

import java.awt.EventQueue;
import java.util.List;

import eu.choreos.ScalabilityReport;

public class ScalabilityReportChart {
    public void createChart(final List<ScalabilityReport> reports) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                XYChart demo = new XYChart("JFreeChartDemo", "ScalabilityTestReport", reports);
                demo.pack();
                demo.setLocationRelativeTo(null);
                demo.setVisible(true);
            }
         });
    }
}
