package eu.choreos;

import eu.choreos.chart.ScalabilityReportChart;


public class ChartCreation {
	public static void countChart() throws Exception {
		SumCount sumCount = new SumCount();
		ScalabilityReport report = ScalabilityTesting.run(sumCount, "count", 1);
		ScalabilityReportChart chart = new ScalabilityReportChart();
		chart.createChart(report);
	}
	
	public static void sumBothChart() throws Exception {
		SumCount sumCount = new SumCount();
		ScalabilityReport report = ScalabilityTesting.run(sumCount, "sumBoth", 1, 2);
		ScalabilityReportChart chart = new ScalabilityReportChart();
		chart.createChart(report);
	}
	
	
	public static void withoutScaleParameterChart() throws Exception {
		SumCount sumCount = new SumCount();
		ScalabilityReport report = ScalabilityTesting.run(sumCount, "withoutScaleParameter", 2);
		ScalabilityReportChart chart = new ScalabilityReportChart();
		chart.createChart(report);
	}
	
	
	public static void main(String[] args) throws Exception {
		countChart();
		sumBothChart();
		withoutScaleParameterChart();
	}
}
