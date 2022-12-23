package com.eriklievaart.trainer.web;

import com.eriklievaart.toolkit.lang.api.str.Str;

public class Remaining {

	public long rehearse;
	public long total;

	@Override
	public String toString() {
		return rehearse == total || rehearse <= 1 ? "" + total : Str.sub("$/$", rehearse, total);
	}
}
