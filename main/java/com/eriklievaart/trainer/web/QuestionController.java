package com.eriklievaart.trainer.web;

import java.util.List;

import com.eriklievaart.jl.core.api.Bean;
import com.eriklievaart.jl.core.api.Parameters;
import com.eriklievaart.jl.core.api.RequestContext;
import com.eriklievaart.jl.core.api.page.AbstractTemplateController;
import com.eriklievaart.toolkit.lang.api.str.Str;

public class QuestionController extends AbstractTemplateController {

	@Bean
	private RequestContext context;
	@Bean
	private Parameters parameters;

	private State state;

	public QuestionController(State state) {
		this.state = state;
	}

	@Override
	public void invoke() throws Exception {
		if (parameters.contains("restart")) {
			state.reload();

		} else {
			processAnswer();
		}
		render();
	}

	private void processAnswer() {
		parameters.getOptional("answer").ifPresent(answer -> {
			Question removed = state.questions.remove(0);
			if (isValid(removed.getAnswers(), answer)) {
				state.correct(removed);
			} else {
				state.incorrect(removed);
				model.put("previous", removed);
				model.put("answer", answer);
				state.questions.add(removed);
			}
		});
	}

	private void render() {
		if (state.questions.isEmpty()) {
			setTemplate("/web/freemarker/complete.ftlh");
		} else {
			model.put("remaining", "" + state.questions.size());
			model.put("question", state.questions.get(0));
			setTemplate("/web/freemarker/question.ftlh");
		}
	}

	private boolean isValid(List<String> list, String answer) {
		for (String expect : list) {
			if (Str.isEqualIgnoreCase(strip(expect), strip(answer))) {
				return true;
			}
		}
		return false;
	}

	private String strip(String answer) {
		return answer.replaceAll("[ _]", "");
	}
}
