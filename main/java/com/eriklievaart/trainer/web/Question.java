package com.eriklievaart.trainer.web;

public class Question {

	private String query;
	private String answer;

	public Question(String query, String answer) {
		this.query = query;
		this.answer = answer;
	}

	public String getQuery() {
		return query;
	}

	public String getAnswer() {
		return answer;
	}
}
