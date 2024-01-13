package com.eriklievaart.trainer.web.answer;

public abstract class AbstractAnswerValidator implements AnswerValidator {

	protected String strip(String answer) {
		return answer.replaceAll("[ _,]", "").replaceAll("\\s++$", "");
	}
}
