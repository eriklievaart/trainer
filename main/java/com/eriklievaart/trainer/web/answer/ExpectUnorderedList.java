package com.eriklievaart.trainer.web.answer;

import java.util.Arrays;
import java.util.List;

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
		String remaining = strip(input).toUpperCase();
		List<String> expect = ListTool.map2(list, this::strip, String::toUpperCase);

		outer: while (!expect.isEmpty()) {
			for (int e = 0; e < expect.size(); e++) {
				for (String alternative : getAlternativesLongestFirst(expect.get(e))) {
					if (remaining.startsWith(alternative)) {
						expect.remove(e);
						remaining = remaining.substring(alternative.length());
						continue outer;
					}
				}
			}
			return false;
		}
		return Str.isEmpty(strip(remaining));
	}

	private List<String> getAlternativesLongestFirst(String appel) {
		return ListTool.sortedCopy(Arrays.asList(appel.split("\\|")), (a, b) -> b.length() - a.length());
	}
}
