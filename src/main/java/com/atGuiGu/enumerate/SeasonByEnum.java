package com.atGuiGu.enumerate;

public enum SeasonByEnum implements GetSeasonInfo {

	// 声明这一行就创建了四个实例
	SPRING("Spring", "aaaa"), SUMMER("Summer", "bbbb"), AUTUMN("Autumn", "cccc"), WINTER("Winter", "dddd");

	// n
	private final String name;
	private final String desc;

	private SeasonByEnum(final String nAME, final String dESC) {
		name = nAME;
		desc = dESC;
	}

	public String getDesc() {
		return desc;
	}

	// 可实现接口，也可以在前面声明时用匿名类来实现接口
	@Override
	public String getinfo() {
		switch (this) {
		case SPRING:
			return "a---";
		case SUMMER:
			return "b---";
		case AUTUMN:
			return "c---";
		case WINTER:
			return "d---";
		default:
			break;
		}
		return null;
	}

	public String getName() {
		return name;
	}
}
