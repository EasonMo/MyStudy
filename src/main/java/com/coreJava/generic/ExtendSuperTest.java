package com.coreJava.generic;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

public class ExtendSuperTest {

	public static void printExtend1(final Pair<? extends Employee> pair) {
		System.out.println(pair.getFirst().getName() + "," + pair.getSecond().getName());
	}

	public static void printExtend2(final Pair<? super Manager> pair) {
		System.out.println(pair.getFirst() + "," + pair.getSecond());
	}

	public static <T extends Comparable<? super T>> T min(T[] a) {
		System.out.println(a.getClass());
		return a[0];
	}
	public static <T extends Comparable<T>> T min2(T[] a) {
		System.out.println(a.getClass());
		return a[0];
	}

	@Test
	public void testExtendSuper() {

		final Employee e1 = new Employee("e1", 2.3, 1, 2, 3);
		final Employee e2 = new Employee("e2", 2.3, 1, 2, 3);
		final Manager m1 = new Manager("e1", 2.3, 1, 2, 3);
		final Manager m2 = new Manager("e2", 2.3, 1, 2, 3);

		final Pair<Employee> pair1 = new Pair<>(e1, e2);

		// Employee和Manager是继承关系，但Pair<Employee>和Pair<Manager>没有关系，不能转换
		// final Pair<Manager> pair2 = pair1;

		final Pair<Manager> pair3 = new Pair<>(m1, m2);
		// pair3.setFirst(e1);

		final Pair<String> pair4 = new Pair<>("str1", "str2");

		ExtendSuperTest.printExtend1(pair1);

		// 限定用泛型类定义的方法的参数类型
		// ExtendTest.printExtend1(pair4);

		Pair<? extends Employee> pair5 = pair1;
		pair5 = pair3;

		pair5.getFirst();
		// pair5.setFirst(e1);
		// pair5.setFirst(m1);

		final Pair<? super Employee> pair6 = new Pair<>(m1, m2);

		pair6.setFirst(m2);
		pair6.setFirst(e1);

		// pair6.setFirst("123");

		// Employee pe = pair6.getFirst();

		final Pair<? super Manager> pair7 = new Pair<>(m1, m2);
		// final Pair<? super Employee> pair7 = new Pair<>(m1, m2);

		pair7.setFirst(m2);
		// 只能set超类为Manager
		// pair7.setFirst(e1);
		// 不能get数据
		// Manager ms = pair7.getFirst();
		Object ms = pair7.getFirst();

		// pair7.setFirst("123");

		// Employee pe = pair7.getFirst();

		// super另一种用法，没什么卵用
		System.out.println("test super:");
		Employee[] eg = {e1, e2};

		Manager[] mg = {m1, m2};

		ExtendSuperTest.min(eg);
		ExtendSuperTest.min(mg);
		ExtendSuperTest.min2(eg);
		ExtendSuperTest.min2(mg);

		System.out.println("test end");
	}

	static class Employee implements Comparable<Employee> {
		private final Date hireDay;

		private final String name;

		private double salary;

		public Employee(final String n, final double s, final int year, final int month, final int day) {
			name = n;
			salary = s;
			final GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
			hireDay = calendar.getTime();
		}

		public Date getHireDay() {
			return hireDay;
		}

		public String getName() {
			return name;
		}

		public double getSalary() {
			return salary;
		}

		public void raiseSalary(final double byPercent) {
			final double raise = salary * byPercent / 100;
			salary += raise;
		}

		@Override
		public int compareTo(Employee o) {
			return 0;
		}
	}

	static class Manager extends Employee {
		private double bonus;

		public Manager(final String n, final double s, final int year, final int month, final int day) {
			super(n, s, year, month, day);
			bonus = 0;
		}

		@Override
		public double getSalary() {
			final double baseSalary = super.getSalary();
			return baseSalary + bonus;
		}

		public void setBonus(final double b) {
			bonus = b;
		}
	}

	static class Pair<T> {

		private T first;
		private T second;

		public Pair(final T first, final T second) {
			this.first = first;
			this.second = second;
		}

		public T getFirst() {
			return first;
		}

		public void setFirst(final T first) {
			this.first = first;
		}

		public T getSecond() {
			return second;
		}

		public void setSecond(final T second) {
			this.second = second;
		}

	}
}
