package eu.choreos;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ScalabilityTesting {

	private static ScalabilityFunction function;
	private static int times;
	private static ScalabilityTestMethod method;

	public static double run(Object scalabilityTests, String methodName, Object... params)
			throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException,
			InstantiationException {

		method = new ScalabilityTestMethod(scalabilityTests, methodName);
		function = method.getScalabilityFunctionObject();
		times = method.getNumberOfTimesToExecute();
		return executeIncreasingParams(scalabilityTests, params);
	}

	public static double executeIncreasingParams(Object scalabilityTestingObject, Object... params)
			throws IllegalAccessException, InvocationTargetException, InstantiationException {
		double total = 0.0;
		Object[] actualParams = Arrays.copyOf(params, params.length);
		Annotation[][] parametersAnnotation = method.getParameterAnnotations();
		for (int i = 0; i < times; i++) {
			total += invokeMethodWithParamsUpdated(scalabilityTestingObject, actualParams);
			increaseEachParamOfParamsArray(actualParams, parametersAnnotation, params);
		}
		return total / times;
	}

	private static Double invokeMethodWithParamsUpdated(Object scalabilityTestingObject, Object[] actualParams) throws IllegalAccessException, InvocationTargetException {
		Number value = (Number) method.invoke(actualParams);
		return value.doubleValue();
	}

	private static void increaseEachParamOfParamsArray(Object[] actualParams,
			Annotation[][] parametersAnnotation, Object... params) {
		for (int j = 0; j < params.length; j++) {
			if (parameterHasScaleAnnotation(parametersAnnotation[j]))
				actualParams[j] = function.increaseParams((Integer) actualParams[j], (Integer) params[j]);
		}
	}

	private static boolean parameterHasScaleAnnotation(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if (annotation instanceof Scale)
				return true;
		}
		return false;
	}

}
