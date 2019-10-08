package com.atGuiGu.reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import org.junit.Test;

public class ProxyTest {

	@Test
	public void testProxy() {
		final ArithmeticCalculator arithmeticCalculator = new ArithmeticCalcultorImpl();
		// ClassLoader : 由动态代理产生的对象由哪个类加载器来加载，通常情况下和被代理对象使用一样的类加载器
		// Class<?>[] : 由动态代理产生的对象必须需要实现在接口的 Class 数组，一定是接口
		// InvocationHandler : 当具体调用代理对象的方法时，将产生什么行为
		final ArithmeticCalculator proxy = (ArithmeticCalculator) Proxy.newProxyInstance(
				arithmeticCalculator.getClass().getClassLoader(), new Class[] { ArithmeticCalculator.class },
				new InvocationHandler() {

					// proxy :
					// method : 正在被调用的方法
					// args : 调用方法时传入的参数
					@Override
					public Object invoke(final Object proxy, final Method method, final Object[] args)
							throws Throwable {
						System.out.println("invoke....");
						System.out.println("method: " + method);
						System.out.println("args:" + Arrays.asList(args));

						// 调用被代理类的目标方法
						final Object result = method.invoke(arithmeticCalculator, args);
						return result;
					}
				});

		proxy.mul(1, 2);

		final int result = proxy.add(2, 5);
		System.out.println(result);
	}

	// 关于动态代理的细节
	// 1. 需要一个被代理的对象
	// 2. 类加载器通常是和被代理对象使用相同的类加载器
	// 3. 一般的，Proxy.newProxyInstance()的返回值一定是一个被代理对象实现的接口的类型，当然也可以是其他的接口的类型
	// 注意：第二个参数，必须是一个接口类型的数组
	// 提示：若代理对象不需要额外实现被代理对象实现的接口以外的接口，可以使用target.getClass().getInterfaces()
	// 4. InvocationHandler 通常使用匿名内部类的方式：被代理对象需要是 final 类型的
	// 5. InvocationHandler 的 invoke() 方法中的第一个参数 Object 类型的 proxy
	// 指的是正在被返回的那个代理对象，一般情况下不使用它

	@Test
	public void testProxy2() {
		final ArithmeticCalculator target = new ArithmeticCalcultorImpl();
		System.out.println(Arrays.asList(target.getClass().getInterfaces()));

		final Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(),
				target.getClass().getInterfaces()
				// new Class[]{ArithmeticCalculator.class, Validator.class}
				, new InvocationHandler() {

					@Override
					public Object invoke(final Object proxy, final Method method, final Object[] args)
							throws Throwable {
						// System.out.println(proxy.toString());
						return method.invoke(target, args);
					}
				});

		final ArithmeticCalculator arithmeticCalculator = (ArithmeticCalculator) proxy;

		System.out.println(arithmeticCalculator.add(4, 2));
	}
}
