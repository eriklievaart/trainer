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

	@Test
	public void isValidOutOfOrder() {
		ExpectUnorderedList validate = new ExpectUnorderedList(Arrays.asList("boeren|boerenkool", "sla"));

		Check.isTrue(validate.isValid("boeren sla"));
		Check.isTrue(validate.isValid("boerenkool sla"));
		Check.isTrue(validate.isValid("sla boeren"));
		Check.isTrue(validate.isValid("sla boerenkool"));

		Check.isFalse(validate.isValid("sla kool boeren"));
	}

	@Test
	public void isValidMax5() {
		ExpectUnorderedList validate = new ExpectUnorderedList(Arrays.asList("1", "2", "3", "4", "5", "6"));

		Check.isTrue(validate.isValid("1 2 3 4 5 6"));
		Check.isTrue(validate.isValid("1 2 3 4 5"));
		Check.isFalse(validate.isValid("1 2 3 4"));
		Check.isFalse(validate.isValid("1 2 3"));
		Check.isFalse(validate.isValid("1 2"));
		Check.isFalse(validate.isValid("1"));

		Check.isTrue(validate.isValid("1 2 3 4 5"));
		Check.isTrue(validate.isValid("1 2 3 4 6"));
		Check.isTrue(validate.isValid("1 2 3 5 6"));
		Check.isTrue(validate.isValid("1 2 4 5 6"));
		Check.isTrue(validate.isValid("1 3 4 5 6"));
		Check.isTrue(validate.isValid("2 3 4 5 6"));
	}
}
