package com.eriklievaart.trainer.web;

import com.eriklievaart.jl.core.api.Bean;
import com.eriklievaart.jl.core.api.Parameters;
import com.eriklievaart.jl.core.api.RequestContext;
import com.eriklievaart.jl.core.api.page.AbstractTemplateController;
import com.eriklievaart.toolkit.lang.api.str.Str;

public class QuestionController extends AbstractTemplateController {

	@Bean
	private RequestContext context;

	private State state;

	public QuestionController(State state) {
		this.state = state;
	}

	@Override
	public void invoke() throws Exception {
		Parameters parameters = context.getParameterSupplier().get();
		if (parameters.contains("restart")) {
			state.reload();
		}
		parameters.getOptional("answer").ifPresent(answer -> {
			Question removed = state.questions.remove(0);
			if (!isValid(removed.getAnswer(), answer)) {
				model.put("previous", removed);
				model.put("answer", answer);
				state.questions.add(removed);
			}
		});
		if (state.questions.isEmpty()) {
			setTemplate("/web/freemarker/complete.ftlh");
		} else {
			model.put("remaining", "" + state.questions.size());
			render(state.questions.get(0));
		}
	}

	private boolean isValid(String expected, String answer) {
		return Str.isEqualIgnoreCase(strip(expected), strip(answer));
	}

	private String strip(String answer) {
		return answer.replaceAll("[ ]", "");
	}

	private void render(Question question) {
		model.put("question", question);
		setTemplate("/web/freemarker/question.ftlh");
	}
}
