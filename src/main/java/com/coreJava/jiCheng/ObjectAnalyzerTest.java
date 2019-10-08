package com.coreJava.jiCheng;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * This program uses reflection to spy on objects.
 * 
 * @version 1.11 2004-02-21
 * @author Cay Horstmann
 */
public class ObjectAnalyzerTest {
	public static void main(String[] args) {
		// final ArrayList<Integer> squares = new ArrayList<Integer>();
		// for (int i = 1; i <= 5; i++) {
		// squares.add(i * i);
		// System.out.println(new ObjectAnalyzer().toString(squares));
		// }
		System.out.println(new ObjectAnalyzer().toString(new Manager("Tom",
				2000, 2010, 1, 1)));
	}
}

class ObjectAnalyzer {

	private final ArrayList<Object> visited = new ArrayList<Object>();

	/**
	 * Converts an object to a string representation that lists all fields.
	 * 
	 * @param obj
	 *            an object
	 * @return a string with the object's class name and all field names and
	 *         values
	 */
	public String toString(Object obj) {
		if (obj == null) {
			return "null";
		}
		if (visited.contains(obj)) {
			return "...";
		}
		visited.add(obj);
		Class cl = obj.getClass();
		if (cl == String.class) {
			return (String) obj;
		}
		if (cl.isArray()) {
			String r = cl.getComponentType() + "[]{";
			for (int i = 0; i < Array.getLength(obj); i++) {
				if (i > 0) {
					r += ",";
				}
				final Object val = Array.get(obj, i);
				if (cl.getComponentType().isPrimitive()) {
					r += val;
				} else {
					r += toString(val);
				}
			}
			return r + "}";
		}

		String r = cl.getName();
		// inspect the fields of this class and all superclasses
		do {
			r += "[";
			final Field[] fields = cl.getDeclaredFields();
			AccessibleObject.setAccessible(fields, true);
			// get the names and values of all fields
			for (final Field f : fields) {
				if (!Modifier.isStatic(f.getModifiers())) {
					if (!r.endsWith("[")) {
						r += ",";
					}
					r += f.getName() + "=";
					try {
						final Class t = f.getType();
						final Object val = f.get(obj);
						if (t.isPrimitive()) {
							r += val;
						} else {
							r += toString(val);
						}
					} catch (final Exception e) {
						e.printStackTrace();
					}
				}
			}
			r += "]";
			cl = cl.getSuperclass();
		} while (cl != null);

		return r;
	}
}
