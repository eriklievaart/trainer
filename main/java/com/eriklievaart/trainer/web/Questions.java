package com.eriklievaart.trainer.web;

import java.io.InputStream;
import java.util.List;

import com.eriklievaart.toolkit.io.api.LineFilter;
import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckStr;
import com.eriklievaart.toolkit.lang.api.collection.ListTool;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.trainer.web.answer.AnswerValidator;
import com.eriklievaart.trainer.web.answer.ExpectAnyInCollection;
import com.eriklievaart.trainer.web.answer.ExpectUnorderedList;

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
		result.add(createQuestion(answersToQuestion[1], parseAnswers(answersToQuestion[0])));
	}

	private static Question createQuestion(String query, AnswerValidator answers) {
		if (query.startsWith("[")) {
			CheckStr.contains(query, "]");
			String[] imgToQuestion = query.substring(1).split("]", 2);

			Question question = new Question(imgToQuestion[1].trim(), answers);
			question.setImg(imgToQuestion[0].trim());
			return question;

		} else {
			return new Question(query, answers);
		}
	}

	private static void parseQuestionMark(List<Question> result, String line) {
		String[] questionToAnswers = line.split("\\s*\\?\\s*", 2);
		AssertionException.on(questionToAnswers.length != 2, "*ERROR* Invalid question: $", line);
		result.add(createQuestion(questionToAnswers[0] + "?", parseAnswers(questionToAnswers[1])));
	}

	private static AnswerValidator parseAnswers(String answer) {
		Check.notNull(answer);
		if (answer.trim().startsWith("::")) {
			return parseUnorderedList(answer);
		}
		List<String> collection = ListTool.of(answer.split("\\s*+\\|\\s*+"));
		return new ExpectAnyInCollection(ListTool.map(collection, s -> s.replaceAll("`", "|")));
	}

	private static AnswerValidator parseUnorderedList(String raw) {
		String[] entries = raw.split("\\s*+::++\\s*+");
		return new ExpectUnorderedList(ListTool.filter(entries, e -> Str.notBlank(e)));
	}
}
