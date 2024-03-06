package com.eriklievaart.trainer.web.io;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.mock.BombSquad;
import com.eriklievaart.trainer.web.answer.ExpectExactWithOr;
import com.eriklievaart.trainer.web.controller.Question;

public class QuestionLoaderU {

	@Test
	public void findDuplicatesOne() {
		List<Question> questions = NewCollection.list();
		questions.add(new Question("A", new ExpectExactWithOr(Arrays.asList("A"))));
		QuestionLoader.findDuplicates(questions); // no exception
	}

	@Test
	public void findDuplicatesTwo() {
		List<Question> questions = NewCollection.list();
		questions.add(new Question("A", new ExpectExactWithOr(Arrays.asList("A"))));
		questions.add(new Question("B", new ExpectExactWithOr(Arrays.asList("B"))));
		QuestionLoader.findDuplicates(questions); // no exception
	}

	@Test
	public void findDuplicatesConflict() {
		List<Question> questions = NewCollection.list();
		questions.add(new Question("A", new ExpectExactWithOr(Arrays.asList("A"))));
		questions.add(new Question("A", new ExpectExactWithOr(Arrays.asList("B"))));

		BombSquad.diffuse(RuntimeIOException.class, "duplicate hash", () -> {
			QuestionLoader.findDuplicates(questions);
		});
	}
}
