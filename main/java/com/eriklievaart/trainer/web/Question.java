package com.eriklievaart.trainer.web;

import java.util.Collections;
import java.util.List;

import com.eriklievaart.toolkit.io.api.sha1.Sha1;

public class Question {

	private String query;
	private String img;
	private List<String> answer;

	public Question(String query, List<String> answer) {
		this.query = query;
		this.answer = answer;
	}

	public String getQuery() {
		return query;
	}

	public void setImg(String value) {
		img = value;
	}

	public String getImg() {
		return img;
	}

	public List<String> getAnswers() {
		return Collections.unmodifiableList(answer);
	}

	public String getHash() {
		return Sha1.hash(query);
	}

	@Override
	public String toString() {
		return query;
	}
}
