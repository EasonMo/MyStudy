package com.coreJava.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

class Employee implements Serializable {
	private String name;

	private double salary;

	private Date hireDay;

	public Employee() {
	}

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
	public String toString() {
		return getClass().getName() + "[name=" + name + ",salary=" + salary + ",hireDay=" + hireDay + "]";
	}
}

class Manager extends Employee {
	private Employee secretary;

	/**
	 * Constructs a Manager without a secretary
	 * 
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
	public Manager(final String n, final double s, final int year, final int month, final int day) {
		super(n, s, year, month, day);
		secretary = null;
	}

	/**
	 * Assigns a secretary to the manager.
	 * 
	 * @param s
	 *            the secretary
	 */
	public void setSecretary(final Employee s) {
		secretary = s;
	}

	@Override
	public String toString() {
		return super.toString() + "[secretary=" + secretary + "]";
	}
}

/**
 * @version 1.10 17 Aug 1998
 * @author Cay Horstmann
 */
class ObjectStreamTest {
	public static void main(final String[] args) {
		final Employee harry = new Employee("Harry Hacker", 50000, 1989, 10, 1);
		final Manager carl = new Manager("Carl Cracker", 80000, 1987, 12, 15);
		carl.setSecretary(harry);
		final Manager tony = new Manager("Tony Tester", 40000, 1990, 3, 15);
		tony.setSecretary(harry);

		final Employee[] staff = new Employee[3];

		staff[0] = carl;
		staff[1] = harry;
		staff[2] = tony;

		try {
			// save all employee records to the file employee.dat
			final ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("employee.dat"));
			out.writeObject(staff);
			out.close();

			// retrieve all records into a new array
			final ObjectInputStream in = new ObjectInputStream(new FileInputStream("employee.dat"));
			final Employee[] newStaff = (Employee[]) in.readObject();
			in.close();

			// raise secretary's salary
			newStaff[1].raiseSalary(10);

			// print the newly read employee records
			for (final Employee e : newStaff) {
				System.out.println(e);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
