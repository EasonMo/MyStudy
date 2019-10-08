package com.bjsxt.junit4.test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bjsxt.junit4.T;

public class TTest {
	@AfterClass
	public static void afterClass() {
		System.out.println("afterClass");
	}

	@BeforeClass
	public static void beforeClass() {
		System.out.println("beforeClass");
	}

	@After
	public void after() {
		System.out.println("after");
	}

	@Before
	public void before() {
		System.out.println("before");
	}

	@Test
	public void testAdd() {
		final int z = new T().add(5, 3);
		// assertThat( z, is(8));
		assertThat("运算结果错误", z, is(9));
		assertThat(z, allOf(greaterThan(5), lessThan(10)));
		// int a = 8/0;
	}

	@Test(expected = java.lang.ArithmeticException.class, timeout = 100)
	public void testDivide() {
		final int z = new T().divide(8, 0);

	}

}
