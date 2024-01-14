package com.eriklievaart.trainer.web.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.eriklievaart.toolkit.io.api.ResourceTool;
import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.logging.api.LogTemplate;
import com.eriklievaart.trainer.web.controller.Question;
import com.eriklievaart.trainer.web.controller.QuestionParser;

public class QuestionLoader {
	private LogTemplate log = new LogTemplate(getClass());

	private Optional<File> override;

	public QuestionLoader(Optional<File> override) {
		this.override = override;
	}

	public List<Question> loadQuestions(List<String> courses) {
		List<Question> result = new ArrayList<>();
		courses.forEach(course -> result.addAll(loadQuestions(course)));
		return result;
	}

	List<Question> loadQuestions(String course) {
		List<Question> questions = QuestionParser.parse(getInputStream(course));
		questions.forEach(q -> q.setCourse(course));
		return questions;
	}

	private InputStream getInputStream(String course) {
		if (!override.isPresent()) {
			return ResourceTool.getInputStream(QuestionParser.class, Str.sub("/web/questions/$.txt", course));
		}
		try {
			File file = new File(override.get(), course + ".txt");
			log.trace("loading hot: $", file);
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeIOException(e);
		}
	}

	public boolean isModified() {
		return override.isPresent();
	}
}
