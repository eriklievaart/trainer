package com.eriklievaart.trainer.web.answer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.eriklievaart.toolkit.lang.api.collection.ListTool;
import com.eriklievaart.toolkit.lang.api.str.Str;

public class ExpectUnorderedList extends AbstractAnswerValidator {

	private List<String> list;

	public ExpectUnorderedList(List<String> list) {
		this.list = list;
	}

	@Override
	public List<String> getAnswers() {
		return ListTool.map(list, s -> ":: " + s + " ::");
	}

	@Override
	public boolean isValid(String input) {
		List<String> expect = ListTool.map2(list, this::strip, String::toUpperCase);
		AtomicReference<String> remainingInput = new AtomicReference<>(strip(input).toUpperCase());

		int found = 0;
		while (!expect.isEmpty() && findMatch(expect, remainingInput)) {
			found++;
		}
		boolean wrongAnswer = !Str.isEmpty(strip(remainingInput.get()));
		if (wrongAnswer) {
			return false;
		}
		return expect.isEmpty() || found >= 5; // max 5 answers required
	}

	private boolean findMatch(List<String> expect, AtomicReference<String> remainderReference) {
		String remainder = remainderReference.get();

		for (int e = 0; e < expect.size(); e++) {
			for (String alternative : getAlternativesLongestFirst(expect.get(e))) {
				if (remainder.startsWith(alternative)) {
					expect.remove(e);
					remainderReference.set(remainder.substring(alternative.length()));
					return true;
				}
			}
		}
		return false;
	}

	private List<String> getAlternativesLongestFirst(String appel) {
		return ListTool.sortedCopy(Arrays.asList(appel.split("\\|")), (a, b) -> b.length() - a.length());
	}
}
