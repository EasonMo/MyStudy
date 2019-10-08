package com.coreJava.generic;

public class Pair<T> {

	private T first;
	private T second;

	public Pair(final T first, final T second) {
		this.first = first;
		this.second = second;
	}

	public T getFirst() {
		return first;
	}

	public T getSecond() {
		return second;
	}

	public void setFirst(final T first) {
		this.first = first;
	}

	public void setSecond(final T second) {
		this.second = second;
	}
}
