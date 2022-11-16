package com.eriklievaart.trainer.web;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.eriklievaart.toolkit.io.api.FileTool;
import com.eriklievaart.toolkit.io.api.JvmPaths;
import com.eriklievaart.toolkit.io.api.LineFilter;
import com.eriklievaart.toolkit.lang.api.collection.LazyMap;
import com.eriklievaart.toolkit.lang.api.collection.ListTool;
import com.eriklievaart.toolkit.lang.api.date.TimestampTool;
import com.eriklievaart.toolkit.lang.api.str.StringBuilderWrapper;

public class State {

	public List<Question> questions;
	private Map<String, Progress> progression = new LazyMap<>(
			key -> new Progress(System.currentTimeMillis() - TimestampTool.ONE_DAY));

	{
		loadProgress();
		reload();
	}

	public void reload() {
		List<Question> filtered = ListTool.filter(Questions.load(), q -> progression.get(q.getHash()).skip());
		questions = ListTool.shuffledCopy(filtered);
	}

	private void loadProgress() {
		if (!getProgressFile().isFile()) {
			return;
		}
		for (String line : new LineFilter(getProgressFile()).dropBlank().drop(s -> s.split(";").length != 3).list()) {

			String[] keyRightWrong = line.split(";");
			Progress progress = new Progress(Long.parseLong(keyRightWrong[2]));
			progress.lastRight = Long.parseLong(keyRightWrong[1]);

			progression.put(keyRightWrong[0], progress);
		}
	}

	public void correct(Question question) {
		progression.get(question.getHash()).correct();
		storeProgression();
	}

	public void incorrect(Question question) {
		progression.get(question.getHash()).incorrect();
		storeProgression();
	}

	private void storeProgression() {
		StringBuilderWrapper builder = new StringBuilderWrapper();
		progression.forEach((key, p) -> {
			if (p.lastRight != 0) {
				builder.append(key).append(';').append(p.lastRight).append(';').append(p.lastWrong).appendLine();
			}
		});
		FileTool.writeStringToFile(builder.toString(), getProgressFile());
	}

	private File getProgressFile() {
		return new File(JvmPaths.getJarDirOrRunDir(getClass()), "progress.txt");
	}
}
