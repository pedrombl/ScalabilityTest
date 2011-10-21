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

}
