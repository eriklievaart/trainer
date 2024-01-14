package com.eriklievaart.trainer.web;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.eriklievaart.toolkit.io.api.StreamTool;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.CollectionTool;
import com.eriklievaart.trainer.web.controller.Question;
import com.eriklievaart.trainer.web.controller.QuestionParser;

public class QuestionsU {

	@Test
	public void parseEqualsSign() {
		Question question = CollectionTool.getSingle(QuestionParser.parse(StreamTool.toInputStream("2 = 1 + 1")));
		Check.isEqual(question.getQuery(), "1 + 1");
		Assertions.assertThat(question.getAnswers()).containsExactly("2");
	}

	@Test
	public void parseQuestionMark() {
		Question question = CollectionTool.getSingle(QuestionParser.parse(StreamTool.toInputStream("1 + 1? 2")));
		Check.isEqual(question.getQuery(), "1 + 1?");
		Assertions.assertThat(question.getAnswers()).containsExactly("2");
	}

	@Test
	public void parseImage() {
		Question question = CollectionTool.getSingle(QuestionParser.parse(StreamTool.toInputStream("[img.png] wot? uh")));
		Check.isEqual(question.getQuery(), "wot?");
		Check.isEqual(question.getImg(), "img.png");
		Assertions.assertThat(question.getAnswers()).containsExactly("uh");
	}

	@Test
	public void parseAnswerAnyInCollection() {
		Question question = CollectionTool.getSingle(QuestionParser.parse(StreamTool.toInputStream("A of B? A | B")));
		Check.isEqual(question.getQuery(), "A of B?");
		Assertions.assertThat(question.getAnswers()).containsExactly("A", "B");
	}

	@Test
	public void parseAnswerUnorderedList() {
		Question question = CollectionTool.getSingle(QuestionParser.parse(StreamTool.toInputStream("A of B? ::A::B::")));
		Check.isEqual(question.getQuery(), "A of B?");
		Assertions.assertThat(question.getAnswers()).containsExactly(":: A ::", ":: B ::");
	}

	@Test
	public void parseAnswerUnorderedListWithBar() {
		Question question = CollectionTool.getSingle(QuestionParser.parse(StreamTool.toInputStream("has bar? ::a|b::")));
		Check.isEqual(question.getQuery(), "has bar?");
		Assertions.assertThat(question.getAnswers()).containsExactly(":: a|b ::");
		Check.isTrue(question.isValidAnswer("a"));
	}
}
