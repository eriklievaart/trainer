package com.eriklievaart.trainer.web.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.eriklievaart.toolkit.lang.api.collection.LazyMap;
import com.eriklievaart.toolkit.lang.api.collection.ListTool;
import com.eriklievaart.trainer.web.io.QuestionLoader;

public class State {

	private List<String> courses;
	private final QuestionLoader loader;

	public Optional<Question> current;
	private List<Question> questions;
	private LazyMap<String, CourseProgress> courseToProgress = new LazyMap<>(course -> new CourseProgress(course));

	public State(QuestionLoader loader, String course) {
		this(loader, Arrays.asList(course));
	}

	public State(QuestionLoader loader, List<String> courses) {
		this.loader = loader;
		this.courses = courses;

		loadQuestions();
		nextQuestion();
	}

	public void reloadIfModified() {
		if (loader.isModified()) {
			loadQuestions();
		}
	}

	private void loadQuestions() {
		questions = sortUnseenLast(ListTool.shuffledCopy(loader.loadQuestions(courses)));
		nextQuestion();
	}

	@SuppressWarnings("unchecked")
	private List<Question> sortUnseenLast(List<Question> shuffled) {

		Map<Boolean, List<Question>> map = ListTool.partition(shuffled, q -> {
			return getProgress(q).isModified(q);
		});
		List<Question> seen = map.get(true);
		List<Question> unseen = map.get(false);
		return unseen.size() < 10 ? shuffled : ListTool.merge(seen, unseen);
	}

	private void nextQuestion() {
		for (Question question : questions) {
			if (getProgress(question).isValid(question)) {
				current = Optional.of(question);
				return;
			}
		}
		current = Optional.empty();
		return;
	}

	private CourseProgress getProgress(Question question) {
		return courseToProgress.get(question.getCourse());
	}

	public void correct() {
		Question q = current.get();
		getProgress(q).correct(q);
		nextQuestion();
	}

	public void incorrect() {
		Question q = current.get();
		getProgress(q).incorrect(q);
		nextQuestion();
	}

	public Remaining countRemaining() {
		Remaining r = new Remaining();
		courseToProgress.values().forEach(c -> c.increment(r, Collections.unmodifiableList(questions)));
		return r;
	}

	public List<String> getCourses() {
		return Collections.unmodifiableList(courses);
	}
}
