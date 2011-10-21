package eu.choreos;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ScalabilityTesting {

	public static double run(Object scalabilityTestingObject, String methodName, Object... params)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		
		Method method = searchMethodWithScalabilityTestingAnnotation(methodName, scalabilityTestingObject);
		int times = getNumberOfTimesToExecute(method);
		return executeIncreasingLinearly(scalabilityTestingObject, method, times, params);
		
	}

	@SuppressWarnings("rawtypes")
	private static Method searchMethodWithScalabilityTestingAnnotation(String methodName,
			Object scalabilityTestingObject) {
		Class scalabilityTestingClass = scalabilityTestingObject.getClass();
		Method scalabilityTestingMethod = null;
		for (Method method : scalabilityTestingClass.getDeclaredMethods()) {
			if (method.getName().equals(methodName)) {
				scalabilityTestingMethod = method;
			}
		}
		return scalabilityTestingMethod;
	}

	private static int getNumberOfTimesToExecute(Method method) {
		ScalabilityTest scalabilityTest = method.getAnnotation(ScalabilityTest.class);
		int times = scalabilityTest.maxIncreaseTimes();
		return times;
	}

	private static double executeIncreasingLinearly(Object scalabilityTestingObject, Method method, int times,
			Object... params) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		double total = 0.0;
		Object[] actualParams = Arrays.copyOf(params, params.length);

		for (int i = 0; i < times; i++) {
			total += invokeMethodWithParamsUpdated(scalabilityTestingObject, method, actualParams);
			sumEachParamOfParamsArray(method, actualParams, params);
		}
		return total / times;
	}

	private static Integer invokeMethodWithParamsUpdated(Object scalabilityTestingObject, Method method,
			Object[] actualParams) throws IllegalAccessException, InvocationTargetException {
		return (Integer) method.invoke(scalabilityTestingObject, actualParams);
	}

	private static void sumEachParamOfParamsArray(Method method, Object[] actualParams, Object... params) {
		Annotation[][] parameters = method.getParameterAnnotations();
		for (int j = 0; j < params.length; j++) {
			if(parameterHasScaleAnnotation(parameters[j]))
				sumParam(actualParams, j, params);
		}
	}

	private static boolean parameterHasScaleAnnotation(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if(annotation instanceof Scale)
				return true;
		}
		return false;
	}

	private static void sumParam(Object[] actualParams, int j, Object... params) {
		Integer actualParam = ((Integer)actualParams[j]) + ((Integer)params[j]);
		actualParams[j] = actualParam;
	}

}
