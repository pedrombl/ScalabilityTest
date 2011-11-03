package eu.choreos;

public class SumCount {
	
	@ScalabilityTest(maxIncreaseTimes=5)
	public int count(@Scale int number) {
		return number;
	}
	
	@ScalabilityTest(maxIncreaseTimes=5)
	public int sumBothAndCount(@Scale int a, @Scale int b) {
		return a+b;
	}
	
	@ScalabilityTest(maxIncreaseTimes=5)
	public int sumBothMultipleAndCount(@Scale int a, @Scale int b, int multiply) {
		return multiply*(a+b);
	}
	
	@ScalabilityTest(maxIncreaseTimes=5)
	public int withoutScaleParameter(int a) {
		return a;
	}
	
	public int withoutScalabilityTestAnnotation(int a) {
		return a;
	}
	
	@ScalabilityTest(maxIncreaseTimes=5)
	public double countReturningDouble(@Scale int number) {
		double total = 1.0*number;
		return total;
	}

}
