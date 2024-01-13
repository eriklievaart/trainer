package com.eriklievaart.trainer.web.answer;

import java.util.Arrays;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class ExpectUnorderedListU {

	@Test
	public void isValidInAnyOrder() {
		ExpectUnorderedList validate = new ExpectUnorderedList(Arrays.asList("A", "B", "C"));

		Check.isTrue(validate.isValid("A B C"));
		Check.isTrue(validate.isValid("A C B"));
		Check.isTrue(validate.isValid("B A C"));
		Check.isTrue(validate.isValid("B C A"));
		Check.isTrue(validate.isValid("C A B"));
		Check.isTrue(validate.isValid("C B A"));
		Check.isTrue(validate.isValid("a b c"));

		Check.isFalse(validate.isValid("B C"));
		Check.isFalse(validate.isValid("A B"));
		Check.isFalse(validate.isValid("A A B"));
		Check.isFalse(validate.isValid("A A B C"));
	}

	@Test
	public void isValidLogicalOr() {
		ExpectUnorderedList validate = new ExpectUnorderedList(Arrays.asList("A|B", "C|D"));

		Check.isTrue(validate.isValid("A C"));
		Check.isTrue(validate.isValid("B C"));
		Check.isTrue(validate.isValid("A D"));
		Check.isTrue(validate.isValid("B D"));
		Check.isTrue(validate.isValid("a c"));

		Check.isFalse(validate.isValid("A B C"));
		Check.isFalse(validate.isValid("A B"));
	}
}
