package com.coreJava;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This program demonstrates inheritance.
 * 
 * @version 1.21 2004-02-21
 * @author Cay Horstmann
 */
public class ManagerTest {
	/*	public static void main(String[] args) {
			// construct a Manager object
			Manager boss = new Manager("Carl Cracker", 80000, 1987, 12, 15);
			boss.setBonus(5000);

			Employee[] staff = new Employee[3];

			// fill the staff array with Manager and Employee objects

			staff[0] = boss;
			staff[1] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
			staff[2] = new Employee("Tommy Tester", 40000, 1990, 3, 15);

			// print out information about all Employee objects
			for (Employee e : staff)
				System.out.println("name=" + e.getName() + ",salary="
						+ e.getSalary());
		}*/

	public static void main(String[] args) {
		final Employee e = new Employee("Harry Hacker", 50000, 1989, 10, 1);
		System.out.println(e.getClass().getName() + " " + e.getName());

		final Manager m = new Manager("Carl Cracker", 80000, 1987, 12, 15);
		System.out.println(m.getClass().getName() + " " + m.getName());

	}
}

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
