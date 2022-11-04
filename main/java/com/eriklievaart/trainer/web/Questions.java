package com.eriklievaart.trainer.web;

import java.io.InputStream;
import java.util.List;

import com.eriklievaart.toolkit.io.api.LineFilter;
import com.eriklievaart.toolkit.io.api.ResourceTool;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;

public class Questions {

	public static List<Question> load() {
		List<Question> result = NewCollection.list();

		InputStream is = ResourceTool.getInputStream(Questions.class, "/web/questions.txt");
		for (String line : new LineFilter(is).dropBlank().eof().regexReplaceAll("\\s+", " ").trim().list()) {
			String[] questionToAnswer = line.split("\\s*\\?\\s*");
			result.add(new Question(questionToAnswer[0] + "?", questionToAnswer[1]));
		}
		return result;
	}
}
