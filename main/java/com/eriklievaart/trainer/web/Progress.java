package com.eriklievaart.trainer.web;

import com.eriklievaart.toolkit.lang.api.date.TimestampTool;
import com.eriklievaart.toolkit.lang.api.str.Str;

public class Progress {

	public long lastWrong;
	public long lastRight;
	public long validUntil;

	public void correct() {
		lastRight = System.currentTimeMillis();
		calculateValidUntil();
	}

	public void incorrect() {
		lastWrong = System.currentTimeMillis();
		validUntil = lastWrong + randomize(TimestampTool.ONE_MINUTE);
	}

	private long randomize(long fixed) {
		double random = (4 * Math.random() + 1) * fixed;
		return (long) random;
	}

	private void calculateValidUntil() {
		long now = System.currentTimeMillis();

		if (lastWrong == 0) {
			lastWrong = now - TimestampTool.ONE_MINUTE;
		}
		long timePassed = lastRight - lastWrong;
		validUntil = lastRight + randomize(timePassed);
	}

	public boolean skip() {
		return validUntil > System.currentTimeMillis();
	}

	public boolean isModified() {
		return lastRight != 0 || lastWrong != 0;
	}

	@Override
	public String toString() {
		return Str.sub("[$:$]", lastRight, lastWrong);
	}
}
