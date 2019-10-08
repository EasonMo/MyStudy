package com.pattern.singleton;

public class StaticSingleton {
	private static class SingletonHolder {
		private static StaticSingleton instance = new StaticSingleton();
	}

	public static String DESC = "This is Singleton!";

	public static StaticSingleton getInstance() {
		return SingletonHolder.instance;
	}

	public static void main(final String[] args) {
		// StaticSingleton.getInstance();
		System.out.println(StaticSingleton.DESC);
	}

	private StaticSingleton() {
		System.out.println("StaticSingleton is create");
	}
}
