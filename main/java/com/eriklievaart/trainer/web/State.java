package com.eriklievaart.trainer.web;

import java.util.List;

import com.eriklievaart.toolkit.lang.api.collection.ListTool;

public class State {

	public List<Question> questions;

	{
		reload();
	}

	public void reload() {
		questions = ListTool.shuffledCopy(Questions.load());
	}
}
