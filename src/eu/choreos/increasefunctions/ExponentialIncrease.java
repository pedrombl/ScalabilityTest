package eu.choreos.increasefunctions;

public class ExponentialIncrease extends ScalabilityFunction {

	@Override
	public Object increaseParams(int actualParam, int fixedParam) {
		return actualParam*2;
	}

}
