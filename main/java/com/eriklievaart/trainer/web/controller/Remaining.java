package com.eriklievaart.trainer.web.controller;

import com.eriklievaart.toolkit.lang.api.str.Str;

public class Remaining {

	public long rehearse;
	public long total;
	public long timestamp;

	@Override
	public String toString() {
		boolean noSlash = rehearse <= 1 || total - rehearse < 10;
		return noSlash ? "" + total : Str.sub("$/$", rehearse, total);
	}
}
