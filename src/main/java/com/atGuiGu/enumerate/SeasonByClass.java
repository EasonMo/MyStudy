package com.atGuiGu.enumerate;

/**
 * 自己实现的枚举类
 * 
 * @author 莫翌成 2018年3月28日
 *
 */
public class SeasonByClass {

	final public static SeasonByClass SPRING = new SeasonByClass("Spring", "aaaa");
	final public static SeasonByClass SUMMER = new SeasonByClass("Summer", "bbbb");
	final public static SeasonByClass AUTUMN = new SeasonByClass("Autumn", "cccc");
	final public static SeasonByClass WINTER = new SeasonByClass("Winter", "dddd");

	public static void main(final String[] args) {
		System.out.println(SeasonByClass.SPRING);
		System.out.println(new SeasonByClass("s", "b"));
	}

	private final String NAME;
	private final String DESC;

	// 私有化构造器
	private SeasonByClass(final String name, final String desc) {
		this.NAME = name;
		this.DESC = desc;
	}

	public String getDESC() {
		return DESC;
	}

	public String getNAME() {
		return NAME;
	}

	@Override
	public String toString() {
		return NAME + " : " + DESC;
	}

}
