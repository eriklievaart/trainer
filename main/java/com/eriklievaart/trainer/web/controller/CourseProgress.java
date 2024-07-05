package com.eriklievaart.trainer.web.controller;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.eriklievaart.toolkit.io.api.FileTool;
import com.eriklievaart.toolkit.io.api.JvmPaths;
import com.eriklievaart.toolkit.io.api.LineFilter;
import com.eriklievaart.toolkit.lang.api.collection.LazyMap;
import com.eriklievaart.toolkit.lang.api.collection.SetTool;
import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.lang.api.str.StringBuilderWrapper;

public class CourseProgress {

	private String course;
	protected Map<String, Progress> progression = new LazyMap<>(key -> new Progress());

	public CourseProgress(String course) {
		this.course = course;
		loadProgress();
	}

	public boolean isModified(Question q) {
		return progression.get(q.getHash()).isModified();
	}

	protected void storeProgression() {
		StringBuilderWrapper builder = new StringBuilderWrapper();
		progression.forEach((key, p) -> {
			if (p.isModified()) {
				builder.subLine("$;$;$;$", key, p.lastRight, p.lastWrong, p.validUntil);
			}
		});
		FileTool.writeStringToFile(builder.toString(), getProgressFile());
	}

	protected void loadProgress() {
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

	private File getProgressFile() {
		File root = new File(JvmPaths.getJarDirOrRunDir(getClass())).getParentFile();
		return new File(root, Str.sub("data/$.txt", course));
	}

	public boolean isValid(Question question) {
		return progression.get(question.getHash()).validUntil < System.currentTimeMillis();
	}

	public void correct(Question q) {
		progression.get(q.getHash()).correct();
		storeProgression();
	}

	public void incorrect(Question q, int delay) {
		progression.get(q.getHash()).incorrect(delay);
		storeProgression();
	}

	public void increment(Remaining counter, List<Question> questions) {
		long now = System.currentTimeMillis();

		for (Question q : questions) {
			if (!q.getCourse().equals(course)) {
				continue;
			}
			Progress p = progression.get(q.getHash());
			if (p.validUntil < now) {
				counter.total++;
				if (p.isModified()) {
					counter.rehearse++;
				}
			}
		}
	}

	public int countRedundantProgress(List<Question> questions) {
		int redundant = progression.size();

		for (Question q : questions) {
			if (progression.containsKey(q.getHash())) {
				redundant--;
			}
		}
		return redundant;
	}

	public void deleteRedundantProgress(List<Question> questions) {
		Set<String> required = SetTool.map(questions, Question::getHash);

		Iterator<String> iter = progression.keySet().iterator();
		while (iter.hasNext()) {
			String hash = iter.next();
			if (!required.contains(hash)) {
				iter.remove();
			}
		}
		storeProgression();
	}

	public long getWaitTimestamp(List<Question> questions) {
		long timestamp = Long.MAX_VALUE;
		for (Question question : questions) {
			if (question.getCourse().equals(course)) {
				timestamp = Math.min(timestamp, progression.get(question.getHash()).validUntil);
			}
		}
		return timestamp;
	}

	public void addMissingFields(WhiteboxVO vo) {
		vo.setValidUntil(progression.get(vo.getHash()).validUntil);
	}
}
