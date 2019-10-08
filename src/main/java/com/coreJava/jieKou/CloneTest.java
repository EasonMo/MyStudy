package com.coreJava.jieKou;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This program demonstrates cloning.
 * 
 * @version 1.10 2002-07-01
 * @author Cay Horstmann
 */
public class CloneTest {
	public static void main(final String[] args) {
		try {
			final Employee original = new Employee("John Q. Public", 50000);
			original.setHireDay(2000, 1, 1);
			final Employee copy = original.clone();
			copy.raiseSalary(10);
			copy.setHireDay(2002, 12, 31);
			System.out.println("original=" + original);
			System.out.println("copy=" + copy);
		} catch (final CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
}

class Employee implements Cloneable {
	private Date hireDay;

	private final String name;

	private double salary;

	public Employee(final String n, final double s) {
		name = n;
		salary = s;
		hireDay = new Date();
	}

	@Override
	public Employee clone() throws CloneNotSupportedException {
		// call Object.clone()
		final Employee cloned = (Employee) super.clone();

		// clone mutable fields
		cloned.hireDay = (Date) hireDay.clone();

		return cloned;
	}

	public void raiseSalary(final double byPercent) {
		final double raise = salary * byPercent / 100;
		salary += raise;
	}

	/**
	 * Set the hire day to a given date.
	 * 
	 * @param year
	 *            the year of the hire day
	 * @param month
	 *            the month of the hire day
	 * @param day
	 *            the day of the hire day
	 */
	public void setHireDay(final int year, final int month, final int day) {
		final Date newHireDay = new GregorianCalendar(year, month - 1, day).getTime();

		// Example of instance field mutation
		hireDay.setTime(newHireDay.getTime());
	}

	@Override
	public String toString() {
		return "Employee[name=" + name + ",salary=" + salary + ",hireDay=" + hireDay + "]";
	}
}
