package eu.choreos;


public class LinearIncrease extends ScalabilityFunction {

	@Override
	public Object increaseParams(int actualParam, int fixedParam) {
		return actualParam + fixedParam;
	}

}
