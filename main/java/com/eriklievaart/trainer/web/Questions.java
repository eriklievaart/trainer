package com.eriklievaart.trainer.web;

import java.io.InputStream;
import java.util.List;

import com.eriklievaart.toolkit.io.api.LineFilter;
import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.collection.ListTool;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.trainer.web.loader.QuestionLoader;

public class Questions {

	public static List<Question> load(QuestionLoader loader) {
		List<Question> result = NewCollection.list();

		InputStream is = loader.getInputStream();
		for (String line : new LineFilter(is).dropBlank().eof().regexReplaceAll("\\s+", " ").trim().list()) {

			if (line.contains("?")) {
				parseQuestionMark(result, line);
			} else {
				parseEqualsSign(result, line);
			}
		}
		return result;
	}

	private static void parseEqualsSign(List<Question> result, String line) {
		String[] questionToAnswer = line.split("\\s*=\\s*", 2);
		AssertionException.on(questionToAnswer.length != 2, "*ERROR* Invalid question: $", line);
		result.add(new Question(questionToAnswer[1], ListTool.of(questionToAnswer[0].split("\\|"))));
	}

	private static void parseQuestionMark(List<Question> result, String line) {
		String[] questionToAnswer = line.split("\\s*\\?\\s*", 2);
		AssertionException.on(questionToAnswer.length != 2, "*ERROR* Invalid question: $", line);
		result.add(new Question(questionToAnswer[0] + "?", ListTool.of(questionToAnswer[1].split("\\|"))));
	}
}
