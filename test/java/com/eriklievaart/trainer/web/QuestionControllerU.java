package com.eriklievaart.trainer.web;

import java.util.Arrays;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class QuestionControllerU {

	@Test
	public void isValid() {
		Check.isTrue(QuestionController.isValid(Arrays.asList("foo"), "foo"));
		Check.isTrue(QuestionController.isValid(Arrays.asList("bar", "foo"), "foo"));
		Check.isTrue(QuestionController.isValid(Arrays.asList("foo", "bar"), "foo"));
		Check.isTrue(QuestionController.isValid(Arrays.asList("foo"), "f o o"));
		Check.isTrue(QuestionController.isValid(Arrays.asList("foo"), "fo,o"));
		Check.isTrue(QuestionController.isValid(Arrays.asList("fo,o"), "fo,o"));
		Check.isTrue(QuestionController.isValid(Arrays.asList("fo,o"), "foo"));
		Check.isTrue(QuestionController.isValid(Arrays.asList("foo"), "FOO"));
		Check.isFalse(QuestionController.isValid(Arrays.asList("foo"), "bar"));

		Check.isTrue(QuestionController.isValid(Arrays.asList("prophase, prometaphase, metaphase, anaphase, telophase"),
				"prophase prometaphase metaphase anaphase telophase"));
	}
}
