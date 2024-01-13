package com.eriklievaart.trainer.web.answer;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class ExpectAnyInCollectionU {

	@Test
	public void isValid() {
		Check.isTrue(new ExpectAnyInCollection(Arrays.asList("foo")).isValid("foo"));
		Check.isTrue(new ExpectAnyInCollection(Arrays.asList("bar", "foo")).isValid("foo"));
		Check.isTrue(new ExpectAnyInCollection(Arrays.asList("foo", "bar")).isValid("foo"));
		Check.isTrue(new ExpectAnyInCollection(Arrays.asList("foo")).isValid("f o o"));
		Check.isTrue(new ExpectAnyInCollection(Arrays.asList("foo")).isValid("fo,o"));
		Check.isTrue(new ExpectAnyInCollection(Arrays.asList("fo,o")).isValid("fo,o"));
		Check.isTrue(new ExpectAnyInCollection(Arrays.asList("fo,o")).isValid("foo"));
		Check.isTrue(new ExpectAnyInCollection(Arrays.asList("foo")).isValid("FOO"));
		Check.isFalse(new ExpectAnyInCollection(Arrays.asList("foo")).isValid("bar"));

		List<String> list = Arrays.asList("prophase, prometaphase, metaphase, anaphase, telophase");
		Check.isTrue(new ExpectAnyInCollection(list).isValid("prophase prometaphase metaphase anaphase telophase"));
	}

	@Test
	public void isValidCase() {
		Check.isTrue(new ExpectAnyInCollection(Arrays.asList("S")).isValid("s"));
		Check.isTrue(new ExpectAnyInCollection(Arrays.asList("s")).isValid("S"));
	}
}
