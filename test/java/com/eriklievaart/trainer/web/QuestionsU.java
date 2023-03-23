package com.eriklievaart.trainer.web;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.eriklievaart.toolkit.io.api.StreamTool;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.CollectionTool;

public class QuestionsU {

	@Test
	public void parseEqualsSign() {
		Question question = CollectionTool.getSingle(Questions.load(StreamTool.toInputStream("2 = 1 + 1")));
		Check.isEqual(question.getQuery(), "1 + 1");
		Assertions.assertThat(question.getAnswers()).containsExactly("2");
	}

	@Test
	public void parseQuestionMark() {
		Question question = CollectionTool.getSingle(Questions.load(StreamTool.toInputStream("1 + 1? 2")));
		Check.isEqual(question.getQuery(), "1 + 1?");
		Assertions.assertThat(question.getAnswers()).containsExactly("2");
	}

	@Test
	public void parseImage() {
		Question question = CollectionTool.getSingle(Questions.load(StreamTool.toInputStream("[img.png] wot? uh")));
		Check.isEqual(question.getQuery(), "wot?");
		Check.isEqual(question.getImg(), "img.png");
		Assertions.assertThat(question.getAnswers()).containsExactly("uh");
	}
}
