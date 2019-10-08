package com.jvm.hotswap;

public class Hot {
	public void hot() {
		System.out.println(" version 5 : " + this.getClass().getClassLoader());
	}

}
