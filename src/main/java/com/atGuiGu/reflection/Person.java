package com.atGuiGu.reflection;

class Person {
	private int age;
	String name;

	public Person() {
		System.out.println("无参数构造器");
	}

	public Person(final String name, final Integer age) {
		super();
		this.age = age;
		this.name = name;
		System.out.println("有参数构造器");
	}

	public int getAge() {
		return age;
	}

	public String getName() {
		return name;
	}

	@AgeValidator(min = 18, max = 35)
	public void setAge(final int age) {
		this.age = age;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setName(final String name, final Integer age) {
		System.out.println("name: " + name);
		System.out.println("age:" + age);
	}

	@Override
	public String toString() {
		return "对象为：name: " + name + " ; age: " + age;
	}

	private String method2() {
		return "private String method2";
	}

	private void setName(final String name, final Integer age, final Integer abc) {
		System.out.println("name: " + name);
		System.out.println("age:" + age);
	}

	private void test() {

	}
}
