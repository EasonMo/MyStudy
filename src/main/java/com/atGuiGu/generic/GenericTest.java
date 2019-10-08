package com.atGuiGu.generic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

public class GenericTest {
	@Test
	public void helloGeneric() {

		final List<Person> persons2 = new ArrayList<Person>();
		persons2.add(new Person("AA", 12));
		persons2.add(new Person("BB", 13));
		persons2.add(new Person("CC", 14));
		persons2.add(new Person("DD", 15));
		persons2.add(new Student());
		// persons2.add("");

		final Person person2 = persons2.get(2);
		System.out.println(person2);

		System.out.println();
		final List persons = new ArrayList();

		persons.add(new Person("AA", 12));
		persons.add(new Person("BB", 13));
		persons.add(new Person("CC", 14));
		persons.add(new Person("DD", 15));
		for (int i = 0; i < persons.size(); i++) {
			final Object obj = persons.get(i);
			System.out.println(obj);
		}
	}

	@Test
	public void testCollectionWithGeneric() {
		final Set<Person> persons = new TreeSet<Person>(
				new Comparator<Person>() {

					@Override
					public int compare(Person p1, Person p2) {

						return p1.getAge() - p2.getAge();
					}
				});
		persons.add(new Person("AA", 14));
		persons.add(new Person("BB", 13));
		persons.add(new Person("CC", 12));
		persons.add(new Person("DD", 15));

		final Iterator<Person> it = persons.iterator();
		while (it.hasNext()) {
			final Person person = it.next();
			System.out.println(person.getAge());
		}
	}
}
