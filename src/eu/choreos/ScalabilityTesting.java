package eu.choreos;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ScalabilityTesting {

	private static ScalabilityFunction function;
	private static int times;
	private static Method method;

	public static double run(Object scalabilityTests, String methodName, Object... params)
			throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException,
			InstantiationException {

		method = searchScalabilityTestMethod(methodName, scalabilityTests);
		function = getScalabilityFunctionObject();
		times = getNumberOfTimesToExecute();
		return executeIncreasingParams(scalabilityTests, params);
	}

	private static Method searchScalabilityTestMethod(String methodName, Object scalabilityTests)
			throws NoSuchMethodException {
		Class<? extends Object> scalabilityTestingClass = scalabilityTests.getClass();
		Method scalabilityTestingMethod = getMethodWithMethodName(methodName, scalabilityTestingClass);
		verifyIfMethodExistsAndHasAnnotation(methodName, scalabilityTestingMethod);
		return scalabilityTestingMethod;
	}

	private static Method getMethodWithMethodName(String methodName, Class<? extends Object> scalabilityTestingClass) {
		for (Method method : scalabilityTestingClass.getDeclaredMethods()) {
			if (methodIsEqualToMethodName(method, methodName))
				return method;
		}
		return null;
	}

	private static Boolean methodIsEqualToMethodName(Method method, String methodName) {
		return method.getName().equals(methodName);
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
		Number value = (Number) method.invoke(scalabilityTestingObject, actualParams);
		return value.doubleValue();
	}

	private static void increaseEachParamOfParamsArray(Object[] actualParams,
			Annotation[][] parametersAnnotation, Object... params) {
		for (int j = 0; j < params.length; j++) {
			if (parameterHasScaleAnnotation(parametersAnnotation[j]))
				actualParams[j] = function.increaseParams((Integer) actualParams[j], (Integer) params[j]);
		}
	}

	private static void verifyIfMethodExistsAndHasAnnotation(String methodName, Method scalabilityTestingMethod)
			throws NoSuchMethodException {
		verifyIfMethodExists(methodName, scalabilityTestingMethod);
		verifyIfMethodHasScalabilityTestAnnotation(methodName, scalabilityTestingMethod);
	}

	private static void verifyIfMethodExists(String methodName, Method scalabilityTestingMethod)
			throws NoSuchMethodException {
		if (scalabilityTestingMethod == null)
			throw new NoSuchMethodException("Method " + methodName + " does not exist");
	}

	private static void verifyIfMethodHasScalabilityTestAnnotation(String methodName, Method scalabilityTestingMethod)
			throws NoSuchMethodException {
		if (!scalabilityTestingMethod.isAnnotationPresent(ScalabilityTest.class))
			throw new NoSuchMethodException("Method " + methodName + "without annotation ScalabilityTest");
	}

	private static ScalabilityFunction getScalabilityFunctionObject() throws InstantiationException,
			IllegalAccessException {
		ScalabilityTest scalabilityTest = method.getAnnotation(ScalabilityTest.class);
		Class<? extends ScalabilityFunction> functionClass = scalabilityTest.scalabilityFunction();
		ScalabilityFunction function = functionClass.newInstance();
		return function;
	}
	
	private static int getNumberOfTimesToExecute() {
		ScalabilityTest scalabilityTest = method.getAnnotation(ScalabilityTest.class);
		int times = scalabilityTest.maxIncreaseTimes();
		return times;
	}

	private static boolean parameterHasScaleAnnotation(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if (annotation instanceof Scale)
				return true;
		}
		return false;
	}

}
