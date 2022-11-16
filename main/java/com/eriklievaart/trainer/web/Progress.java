package com.eriklievaart.trainer.web;

import com.eriklievaart.toolkit.lang.api.date.TimestampTool;
import com.eriklievaart.toolkit.lang.api.str.Str;

public class Progress {

	public long lastWrong;
	public long lastRight;

	public Progress(long stamp) {
		lastWrong = stamp;
	}

	public void correct() {
		lastRight = System.currentTimeMillis();
	}

	public void incorrect() {
		lastWrong = System.currentTimeMillis();
	}

	public boolean stillValid() {
		if (lastRight == 0) {
			return false;
		}
		if (lastRight < lastWrong) {
			return false;
		}
		long now = System.currentTimeMillis();
		if (now > lastRight + 2 * TimestampTool.ONE_DAY) {
			return false;
		}
		long timePassed = lastRight - lastWrong;
		long validUntil = lastRight + 2 * timePassed;
		return now < validUntil;
	}

	public boolean skip() {
		return !stillValid();
	}

	@Override
	public String toString() {
		return Str.sub("[$:$]", lastRight, lastWrong);
	}
}
