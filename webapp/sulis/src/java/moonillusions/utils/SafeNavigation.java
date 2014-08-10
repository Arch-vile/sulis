package moonillusions.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SafeNavigation {

	
	private Class classToNavigate;
	private String expression;

	public SafeNavigation(Class classToNavigate, String expression) {
		this.classToNavigate = classToNavigate;
		this.expression = expression;
	}

	@Override
	public String toString() {
		
		@SuppressWarnings("rawtypes")
		Class currentClass = this.classToNavigate;
		for(String member : this.expression.split("\\.")) {
			Class foundClass = getField(currentClass, member);
			if(foundClass == null) {
				throw new IllegalArgumentException(String.format("Error evaluating %s, could not get field %s in %s",expression,member,currentClass));
			}
			currentClass = foundClass;
		}
		return this.expression;
		
	}

	private Class getField(Class clazz, String member) {
		
		Class field = getFieldUsingPublicVariable(clazz, member);
		if(field != null)
			return field;
		
		field = getFieldUsingGetter(clazz, member);
		return field;
		
	}

	private Class getFieldUsingGetter(Class clazz, String member) {
		try {
			String produceGetterMethodName = "get" + member.substring(0, 1).toUpperCase()+member.substring(1);
			Method method  = clazz.getMethod(produceGetterMethodName, null);
			return method.getReturnType();
		} catch (Exception e) {
			return null;
		}
	}

	private Class getFieldUsingPublicVariable(Class clazz, String fieldName) {
		try {
			Field field = clazz.getField(fieldName);
			return field.getType();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String prop(Class clazz, String expression){
		return new SafeNavigation(clazz, expression).toString();
	}
	
}
