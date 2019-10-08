package com.bjsxt.junit4.test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.bjsxt.junit4.User;

public class UserTest {

	@Test
	public void testGetName() {
		assertThat(new User().getName(), is("宋慧乔"));
	}

}
