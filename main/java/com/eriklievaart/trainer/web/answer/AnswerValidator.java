package com.eriklievaart.trainer.web.answer;

import java.util.List;

public interface AnswerValidator {

	public boolean isValid(String input);

	public List<String> getAnswers();
}
