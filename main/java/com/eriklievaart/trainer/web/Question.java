package com.eriklievaart.trainer.web;

import java.util.List;

import com.eriklievaart.toolkit.io.api.sha1.Sha1;
import com.eriklievaart.trainer.web.answer.AnswerValidator;

public class Question {

	private String query;
	private String img;
	private AnswerValidator expect;

	public Question(String query, AnswerValidator expect) {
		this.query = query;
		this.expect = expect;
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

	public boolean isValidAnswer(String answer) {
		return expect.isValid(answer);
	}

	public List<String> getAnswers() {
		return expect.getAnswers();
	}

	public String getHash() {
		if (img == null) {
			return Sha1.hash(query);
		}
		return Sha1.hash(query + ":" + img);
	}

	@Override
	public String toString() {
		return query;
	}
}
