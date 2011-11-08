package eu.choreos.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

import eu.choreos.increasefunctions.LinearIncrease;
import eu.choreos.increasefunctions.ScalabilityFunction;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ScalabilityTest {
	Class<? extends ScalabilityFunction> scalabilityFunction() default LinearIncrease.class;
	int maxIncreaseTimes();
}
