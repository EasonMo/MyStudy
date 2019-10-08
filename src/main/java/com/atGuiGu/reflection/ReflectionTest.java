package com.atGuiGu.reflection;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

public class ReflectionTest {

	public Field getField(final Class clazz, final String fieldName) {
		Field field = null;

		for (Class cla = clazz; cla != Object.class; cla = cla.getSuperclass()) {
			try {
				field = cla.getDeclaredField(fieldName);
			} catch (final Exception e) {
			}
		}
		return field;
	}

	public Object getFieldValue(final Object obj, final Field field) throws IllegalAccessException {
		field.setAccessible(true);

		return field.get(obj);
	}

	// 获取clazz的methodName方法，可能是私有方法，也可能在父类中
	public Method getMethod(Class clazz, final String methodName, final Class... parameterTypes) {
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				final Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
				return method;
			} catch (final Exception e) {
				// e.printStackTrace();
			}
		}
		return null;

	}

	public Object invoke(final Object obj, final String methodName, final Object... args) {
		// 获取Method对象
		final Class[] parameterTypes = new Class[args.length];
		for (int i = 0; i < args.length; i++) {
			parameterTypes[i] = args[i].getClass();

		}
		try {
			final Method method = obj.getClass().getMethod(methodName, parameterTypes);
			// 执行方法
			// 返回方法的返回值
			return method.invoke(obj, args);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Object invoke(final String className, final String methodName, final Object... args) {
		Object obj = null;

		try {
			obj = Class.forName(className).newInstance();
			return invoke(obj, methodName, args);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	// methodName可能是父类的方法
	public Object invoke2(final Object obj, final String methodName, final Object... args) {
		// 获取Method对象
		final Class[] parameterTypes = new Class[args.length];
		for (int i = 0; i < args.length; i++) {
			parameterTypes[i] = args[i].getClass();

		}
		try {
			final Method method = getMethod(obj.getClass(), methodName, parameterTypes);

			method.setAccessible(true);
			// 执行方法
			// 返回方法的返回值
			return method.invoke(obj, args);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setFieldValue(final Object obj, final Field field, final Object val) throws IllegalAccessException {
		field.setAccessible(true);
		field.set(obj, val);
	}

	@Test
	public void testAnnotation() throws Exception {
		// final Person person = new Person();
		// person.setAge(10);

		final String className = "test.reflection.Person";

		final Class clazz = Class.forName(className);
		final Object obj = clazz.newInstance();

		final Method method = clazz.getDeclaredMethod("setAge", int.class);

		final int val = -20;
		final Annotation annotation = method.getAnnotation(AgeValidator.class);
		if (annotation != null) {
			if (annotation instanceof AgeValidator) {
				final AgeValidator ageValidator = (AgeValidator) annotation;
				if (val < ageValidator.min() || val > ageValidator.max()) {
					throw new RuntimeException("年龄非法");
				}
			}
		}

		method.invoke(obj, 10);

		System.out.println(obj);

	}

	@Test
	public void testClass() throws ClassNotFoundException {
		Class clazz = null;

		// 得到Class对象
		// 第一种方式
		clazz = Person.class;

		// 第二种方式
		final Object obj = new Person();
		clazz = obj.getClass();

		// 第三种方式，用得最多
		final String className = "test.reflection.Person";
		clazz = Class.forName(className);

		// final Field[] fields = clazz.getDeclaredFields();
		System.out.println();
	}

	@Test
	public void testClassField() throws Exception {
		final String className = "test.reflection.Student";
		final String fieldName = "age"; // 可能为私有，可能在其父类中
		final Object val = 20;

		// 创建 className 对应类的对象，并为其 fieldName 赋值为 val
		Object obj = null;

		final Class clazz = Class.forName(className);
		final Field field = getField(clazz, fieldName);
		obj = clazz.newInstance();
		setFieldValue(obj, field, val);

		final Student stu = (Student) obj;
		System.out.println(stu.getAge());// 20
	}

	@Test
	public void testClassLoader() throws ClassNotFoundException, FileNotFoundException {

		// 获取系统类加载器
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		System.out.println(classLoader);

		classLoader = classLoader.getParent();
		System.out.println(classLoader);

		classLoader = classLoader.getParent();
		System.out.println(classLoader);

		// 测试当前类由哪个类加载器进行加载
		classLoader = Class.forName("test.reflection.Person").getClassLoader();
		System.out.println(classLoader);

		// 测试JDK提供的Object类由哪个类加载器负责加载
		classLoader = Class.forName("java.lang.Object").getClassLoader();
		System.out.println(classLoader);

		// 关于类加载器的一个主要方法，调用getResourceAsStream获取类路径下的文件对应的输入流
		// final InputStream in = new FileInputStream("test.properties");
		final InputStream in = this.getClass().getClassLoader().getResourceAsStream("test.properties");
	}

	@Test
	public void testConstructor() throws Exception {
		final String className = "test.reflection.Person";
		final Class<Person> clazz = (Class<Person>) Class.forName(className);

		// 获取 Constructor 对象的列表
		final Constructor<Person>[] constructors = (Constructor<Person>[]) clazz.getConstructors();

		for (final Constructor<Person> constructor : constructors) {
			System.out.println(constructor);
		}

		// 获取指定 Constructor
		final Constructor<Person> constructor = clazz.getConstructor(String.class, Integer.class);
		System.out.println(constructor);

		// 调用构造器的 newInstance 方法创建对象
		final Object obj = constructor.newInstance("张三", 11);
		System.out.println(obj);

	}

	@Test
	public void testField() throws Exception {
		final String className = "test.reflection.Person";
		final Class clazz = Class.forName(className);

		// 获取 Field 数组
		final Field[] fields = clazz.getDeclaredFields();
		for (final Field field : fields) {
			System.out.println(field.getName());
		}

		// 获取指定名字的 Field
		final Field field = clazz.getDeclaredField("name");
		System.out.println(field.getName());

		final Person person = new Person("ABC", 123);
		// 获取指定对象的 Field 的值
		final Object val = field.get(person);
		System.out.println(val);

		// 设置指定对象的 Field 的值
		field.set(person, "qwe");
		System.out.println(person.getName());

		// 若 Field 是私有的
		final Field field2 = clazz.getDeclaredField("age");
		field2.setAccessible(true);
		System.out.println(field2.get(person));
	}

	@Test
	public void testGetMethod() throws ClassNotFoundException {
		final Class clazz = Class.forName("test.reflection.Student");
		Method method = null;

		// method = getMethod(clazz, "method1", Integer.class);
		// System.out.println(method);

		method = getMethod(clazz, "method2");
		System.out.println(method);
	}

	@Test
	public void testGetSuperClass() throws ClassNotFoundException {
		final String className = "test.reflection.Student";
		final Class clazz = Class.forName(className);
		final Class superClazz = clazz.getSuperclass();

		System.out.println(superClazz);

	}

	@Test
	public void testInvoke() {
		final Object obj = new Person();
		invoke(obj, "setName", "张三", 12);

		// invoke("test.reflection.Person", "setName", "李四", 11);
		invoke("test.reflection.Person", "setName", "李四", 11, 12);// 不能访问私有的方法
	}

	// 测试调用父类的方法
	@Test
	public void testInvoke2() {
		final Object obj = new Student();
		invoke2(obj, "method1", 10);

		final Object result = invoke2(obj, "method2");
		System.out.println(result);
	}

	@Test
	public void testInvokePrivateMethod() throws Exception {
		final Object obj = new Student();
		final Class clazz = obj.getClass();
		final Method method = clazz.getDeclaredMethod("method1", Integer.class);
		System.out.println(method);

		// 若需要通过反射执行私有方法，而访问权限不足，则要先使该方法变为可被访问
		method.setAccessible(true);

		method.invoke(obj, 10);

	}

	@Test
	public void testMethod() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		final Class clazz = Class.forName("test.reflection.Person");
		// 得到类的方法，包括父类的，但不能获取private方法
		final Method[] methods = clazz.getMethods();
		for (final Method method : methods) {
			System.out.println("^" + method.getName());
		}

		// 得到当前类声明的方法，包括私有的
		final Method[] methods2 = clazz.getDeclaredMethods();
		for (final Method method : methods2) {
			System.out.println("~" + method.getName());
		}

		// 获取指定的方法
		Method method = clazz.getDeclaredMethod("setName", String.class);
		System.out.println(method);

		method = clazz.getDeclaredMethod("test");
		System.out.println(method);

		method = clazz.getDeclaredMethod("setName", String.class, Integer.class);
		System.out.println(method);

		// 执行方法
		final Object obj = clazz.newInstance();
		method.invoke(obj, "张三", 12);
	}

	@Test
	public void testNewInstance() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		final String className = "test.reflection.Person";
		final Class clazz = Class.forName(className);

		// 调用的是无参构造器，需要定义有无参构造器
		final Object obj = clazz.newInstance();
		System.out.println(obj);
	}
}
