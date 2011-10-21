package eu.choreos;

import static eu.choreos.ScalabilityTesting.run;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ScalabilityTestingTest {
	
	private final double EPSILON = 0.0001;
	private SumCount sumCount;
	
	@Before
	public void setUp() {
		sumCount = new SumCount();
	}

	@Test
	public void shouldIncreaseTheNumberParameterAndReturnMean() throws Exception {
		assertEquals(15.0/5, run(sumCount, "count", 1), EPSILON);
	}
	
	@Test
	public void shouldIncreaseTheNumberOfBothParameters() throws Exception {
		assertEquals(9.0, run(sumCount, "sumBothAndCount", 1, 2), EPSILON);
	}
	
	@Test
	public void shouldIncreaseTheNumberOfParametersWithAnnotationScale() throws Exception {
		assertEquals(90.0, run(sumCount, "sumBothMultipleAndCount", 1, 2, 10), EPSILON);
	}
	
	@Test
	public void shouldnExecuteMethodIfHasNoParameterWithScaleAnnotation() throws Exception {
		run(sumCount, "withoutScaleParameter", 1);
	}

}
