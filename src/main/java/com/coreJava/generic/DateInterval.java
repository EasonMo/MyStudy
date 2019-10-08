package com.coreJava.generic;

import java.util.Date;

public class DateInterval extends Pair<Date> {

	private DateInterval() {
		super(null, null);
	}

	@Override
	public void setSecond(final Date second) {
		if (second.compareTo(getFirst()) >= 0) {
			super.setSecond(second);
		}
	}
}
