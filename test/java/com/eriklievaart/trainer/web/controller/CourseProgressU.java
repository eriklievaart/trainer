package com.eriklievaart.trainer.web.controller;

import java.util.Arrays;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckCollection;
import com.eriklievaart.trainer.web.answer.ExpectExactWithOr;

public class CourseProgressU {

	@Test
	public void countRedundantEmpty() {
		Question a = new Question("A", new ExpectExactWithOr(Arrays.asList("A")));

		CourseProgress testable = new CourseProgress("dummy") {
			@Override
			protected void loadProgress() {
			}
		};
		Check.isEqual(testable.countRedundantProgress(Arrays.asList(a)), 0);
	}

	@Test
	public void countRedundantMatch() {
		Question a = new Question("A", new ExpectExactWithOr(Arrays.asList("A")));

		CourseProgress testable = new CourseProgress("dummy") {
			@Override
			protected void loadProgress() {
				progression.put(a.getHash(), new Progress());
			}
		};
		Check.isEqual(testable.countRedundantProgress(Arrays.asList(a)), 0);
	}

	@Test
	public void countRedundantMismatch() {
		Question a = new Question("A", new ExpectExactWithOr(Arrays.asList("A")));

		CourseProgress testable = new CourseProgress("dummy") {
			@Override
			protected void loadProgress() {
				progression.put("B", new Progress());
			}
		};
		Check.isEqual(testable.countRedundantProgress(Arrays.asList(a)), 1);
	}

	@Test
	public void deleteRedundant() {
		Question a = new Question("A", new ExpectExactWithOr(Arrays.asList("A")));

		CourseProgress testable = new CourseProgress("dummy") {
			@Override
			protected void loadProgress() {
				progression.put(a.getHash(), new Progress());
				progression.put("B", new Progress());
			}

			@Override
			protected void storeProgression() {
				CheckCollection.isSize(progression, 1);
				Check.isEqual(progression.keySet().iterator().next(), a.getHash());
			}
		};
		testable.deleteRedundantProgress(Arrays.asList(a));
	}
}
