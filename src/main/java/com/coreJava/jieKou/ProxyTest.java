package com.coreJava.jieKou;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Random;

public class ProxyTest {
	public static void main(final String[] args) {
		final Object[] elements = new Object[1000];
		for (int i = 0; i < elements.length; i++) {
			final Integer value = i + 1;
			final InvocationHandler handler = new TraceHandler(value);
			final Object proxy = Proxy.newProxyInstance(null, new Class[] { Comparable.class }, handler);
			elements[i] = proxy;
		}
		final Integer key = new Random().nextInt(elements.length) + 1;
		final int result = Arrays.binarySearch(elements, key);
		if (result >= 0) {
			System.out.println(elements[result]);
		}
	}
}

class TraceHandler implements InvocationHandler {
	private final Object target;

	public TraceHandler(final Object t) {
		target = t;
	}

	@Override
	public Object invoke(final Object proxy, final Method m, final Object[] args) throws Throwable {
		System.out.print(target);
		System.out.print("." + m.getName() + "(");

		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				System.out.print(args[i]);
				if (i < args.length - 1) {
					System.out.print(", ");
				}
			}
		}
		System.out.println(")");
		return m.invoke(target, args);
	}
}
