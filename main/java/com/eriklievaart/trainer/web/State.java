package com.eriklievaart.trainer.web;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.eriklievaart.toolkit.io.api.FileTool;
import com.eriklievaart.toolkit.io.api.JvmPaths;
import com.eriklievaart.toolkit.io.api.LineFilter;
import com.eriklievaart.toolkit.lang.api.collection.LazyMap;
import com.eriklievaart.toolkit.lang.api.collection.ListTool;
import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.lang.api.str.StringBuilderWrapper;
import com.eriklievaart.trainer.web.loader.QuestionLoader;

public class State {

	private final String course;
	private final QuestionLoader loader;

	public Optional<Question> current;
	private List<Question> questions;
	private Map<String, Progress> progression = new LazyMap<>(key -> new Progress());

	public State(QuestionLoader loader, String course) {
		this.loader = loader;
		this.course = course;

		loadProgress();
		loadQuestions();
		nextQuestion();
	}

	public void reloadIfModified() {
		if (loader.isModified()) {
			loadQuestions();
		}
	}

	private void loadQuestions() {
		questions = sortUnseenLast(ListTool.shuffledCopy(Questions.load(loader.getInputStream(course))));
		nextQuestion();
	}

	@SuppressWarnings("unchecked")
	private List<Question> sortUnseenLast(List<Question> shuffled) {

		Map<Boolean, List<Question>> map = ListTool.partition(shuffled, q -> {
			return progression.get(q.getHash()).isModified();
		});
		List<Question> seen = map.get(true);
		List<Question> unseen = map.get(false);
		return unseen.size() < 10 ? shuffled : ListTool.merge(seen, unseen);
	}

	private void nextQuestion() {
		for (Question question : questions) {
			if (progression.get(question.getHash()).validUntil < System.currentTimeMillis()) {
				current = Optional.of(question);
				return;
			}
		}
		current = Optional.empty();
		return;
	}

	public void correct() {
		progression.get(current.get().getHash()).correct();
		storeProgression();
		nextQuestion();
	}

	public void incorrect() {
		progression.get(current.get().getHash()).incorrect();
		storeProgression();
		nextQuestion();
	}

	public Remaining countRemaining() {
		long now = System.currentTimeMillis();
		Remaining r = new Remaining();

		for (Question q : questions) {
			Progress p = progression.get(q.getHash());
			if (p.validUntil < now) {
				r.total++;
				if (p.isModified()) {
					r.rehearse++;
				}
			}
		}
		return r;
	}

	private void loadProgress() {
		File file = getProgressFile();
		if (!file.isFile()) {
			return;
		}
		for (String line : new LineFilter(file).dropBlank().drop(s -> s.split(";").length != 4).list()) {

			String[] keyRightWrongValid = line.split(";");
			Progress progress = new Progress();
			progress.lastRight = Long.parseLong(keyRightWrongValid[1]);
			progress.lastWrong = Long.parseLong(keyRightWrongValid[2]);
			progress.validUntil = Long.parseLong(keyRightWrongValid[3]);

			progression.put(keyRightWrongValid[0], progress);
		}
	}

	private void storeProgression() {
		StringBuilderWrapper builder = new StringBuilderWrapper();
		progression.forEach((key, p) -> {
			if (p.isModified()) {
				builder.subLine("$;$;$;$", key, p.lastRight, p.lastWrong, p.validUntil);
			}
		});
		FileTool.writeStringToFile(builder.toString(), getProgressFile());
	}

	private File getProgressFile() {
		File root = new File(JvmPaths.getJarDirOrRunDir(getClass())).getParentFile();
		return new File(root, Str.sub("data/$.txt", course));
	}
}
