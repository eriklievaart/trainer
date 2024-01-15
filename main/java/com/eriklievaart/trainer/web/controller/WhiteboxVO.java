package com.eriklievaart.trainer.web.controller;

import com.eriklievaart.toolkit.lang.api.date.TimestampTool;

public class WhiteboxVO implements Comparable<WhiteboxVO> {

	private final String hash;
	private final String query;
	private long validUntil;

	public WhiteboxVO(Question question) {
		hash = question.getHash();
		query = question.getQuery();
	}

	public String getHash() {
		return hash;
	}

	public String getQuery() {
		return query;
	}

	public void setValidUntil(long value) {
		this.validUntil = value;
	}

	public String getRemainingTime() {
		long now = System.currentTimeMillis();
		return validUntil <= now ? "" : TimestampTool.humanReadable(validUntil - now);
	}

	@Override
	public int compareTo(WhiteboxVO o) {
		return Long.compare(validUntil, o.validUntil);
	}

	@Override
	public String toString() {
		return "VO";
	}
}
