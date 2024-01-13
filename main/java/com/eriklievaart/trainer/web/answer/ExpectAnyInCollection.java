package com.eriklievaart.trainer.web.answer;

import java.util.Collections;
import java.util.List;

import com.eriklievaart.toolkit.lang.api.check.CheckCollection;
import com.eriklievaart.toolkit.lang.api.str.Str;

public class ExpectAnyInCollection extends AbstractAnswerValidator {

	private List<String> list;

	public ExpectAnyInCollection(List<String> list) {
		CheckCollection.notEmpty(list);
		this.list = list;
	}

	@Override
	public boolean isValid(String input) {
		for (String expect : list) {
			if (Str.isEqualIgnoreCase(strip(expect), strip(input))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<String> getAnswers() {
		return Collections.unmodifiableList(list);
	}
}
