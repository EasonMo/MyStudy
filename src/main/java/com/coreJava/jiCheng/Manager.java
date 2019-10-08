package com.coreJava.jiCheng;

import java.util.Date;
import java.util.GregorianCalendar;

class Employee {
	private final Date hireDay;

	private final String name;

	private double salary;

	public Employee(String n, double s, int year, int month, int day) {
		name = n;
		salary = s;
		final GregorianCalendar calendar = new GregorianCalendar(year,
				month - 1, day);
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

	public void raiseSalary(double byPercent) {
		final double raise = salary * byPercent / 100;
		salary += raise;
	}
}

class Manager extends Employee {
	private double bonus;

	/**
	 * @param n
	 *            the employee's name
	 * @param s
	 *            the salary
	 * @param year
	 *            the hire year
	 * @param month
	 *            the hire month
	 * @param day
	 *            the hire day
	 */
	public Manager(String n, double s, int year, int month, int day) {
		super(n, s, year, month, day);
		bonus = 0;
	}

	@Override
	public double getSalary() {
		final double baseSalary = super.getSalary();
		return baseSalary + bonus;
	}

	public void setBonus(double b) {
		bonus = b;
	}
}
