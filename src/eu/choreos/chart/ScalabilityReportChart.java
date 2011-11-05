package eu.choreos.chart;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import eu.choreos.ScalabilityReport;

public class ScalabilityReportChart {
    public void createChart(final ScalabilityReport report) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
            	List<ScalabilityReport> reports = new ArrayList<ScalabilityReport>();
            	reports.add(report);
                XYChart demo = new XYChart("JFreeChartDemo", "ScalabilityTestReport", reports);
                demo.pack();
                demo.setLocationRelativeTo(null);
                demo.setVisible(true);
            }
         });
    }
}
