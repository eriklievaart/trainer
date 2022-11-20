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
import com.eriklievaart.toolkit.lang.api.str.StringBuilderWrapper;

public class State {

	public Optional<Question> current;

	private List<Question> questions;
	private Map<String, Progress> progression = new LazyMap<>(key -> new Progress());

	{
		loadProgress();
		reload();
		nextQuestion();
	}

	public void reload() {
		questions = ListTool.shuffledCopy(Questions.load());
		nextQuestion();
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

	private void loadProgress() {
		if (!getProgressFile().isFile()) {
			return;
		}
		for (String line : new LineFilter(getProgressFile()).dropBlank().drop(s -> s.split(";").length != 4).list()) {

			String[] keyRightWrongValid = line.split(";");
			Progress progress = new Progress();
			progress.lastRight = Long.parseLong(keyRightWrongValid[1]);
			progress.lastWrong = Long.parseLong(keyRightWrongValid[2]);
			progress.validUntil = Long.parseLong(keyRightWrongValid[3]);

			progression.put(keyRightWrongValid[0], progress);
		}
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

	private void storeProgression() {
		StringBuilderWrapper builder = new StringBuilderWrapper();
		progression.forEach((key, p) -> {
			if (p.lastRight != 0 || p.lastWrong != 0) {
				builder.subLine("$;$;$;$", key, p.lastRight, p.lastWrong, p.validUntil);
			}
		});
		FileTool.writeStringToFile(builder.toString(), getProgressFile());
	}

	private File getProgressFile() {
		return new File(JvmPaths.getJarDirOrRunDir(getClass()), "progress.txt");
	}

	public int countRemaining() {
		long now = System.currentTimeMillis();
		return ListTool.filter(questions, q -> progression.get(q.getHash()).validUntil < now).size();
	}
}
