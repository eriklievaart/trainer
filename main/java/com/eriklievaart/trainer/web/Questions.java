package com.eriklievaart.trainer.web;

import java.io.InputStream;
import java.util.List;

import com.eriklievaart.toolkit.io.api.LineFilter;
import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.collection.ListTool;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;

public class Questions {

	public static List<Question> load(InputStream is) {
		List<Question> result = NewCollection.list();

		for (String line : new LineFilter(is).dropHash().dropBlank().eof().regexReplaceAll("\\s+", " ").trim().list()) {

			if (line.contains("?")) {
				parseQuestionMark(result, line);
			} else {
				parseEqualsSign(result, line);
			}
		}
		return result;
	}

	private static void parseEqualsSign(List<Question> result, String line) {
		String[] answersToQuestion = line.split("\\s*=\\s*", 2);
		AssertionException.on(answersToQuestion.length != 2, "*ERROR* Invalid question: $", line);
		result.add(new Question(answersToQuestion[1], parseAnswers(answersToQuestion[0])));
	}

	private static void parseQuestionMark(List<Question> result, String line) {
		String[] questionToAnswers = line.split("\\s*\\?\\s*", 2);
		AssertionException.on(questionToAnswers.length != 2, "*ERROR* Invalid question: $", line);
		result.add(new Question(questionToAnswers[0] + "?", parseAnswers(questionToAnswers[1])));
	}

	private static List<String> parseAnswers(String answer) {
		return ListTool.map(ListTool.of(answer.split("\\|")), s -> s.replaceAll("`", "|"));
	}
}
