package com.eriklievaart.trainer.web;

import java.util.function.Supplier;

import com.eriklievaart.jl.core.api.Bean;
import com.eriklievaart.jl.core.api.Parameters;
import com.eriklievaart.jl.core.api.RequestContext;
import com.eriklievaart.jl.core.api.page.AbstractTemplateController;

public class QuestionController extends AbstractTemplateController {

	@Bean
	private RequestContext context;
	@Bean
	private Parameters parameters;

	private Supplier<State> state;

	public QuestionController(Supplier<State> supplier) {
		this.state = supplier;
	}

	@Override
	public void invoke() throws Exception {
		processAnswer();
		render();
	}

	private void processAnswer() {
		parameters.getOptional("answer").ifPresent(answer -> {
			Question query = state.get().current.get();

			if (!query.getHash().equals(parameters.getString("hash"))) {
				model.put("mismatch", true);
			} else if (query.isValidAnswer(answer)) {
				state.get().correct();
			} else {
				state.get().incorrect();
				model.put("previous", query);
				model.put("answer", answer);
			}
		});
		state.get().reloadIfModified();
	}

	private void render() {
		model.putIfAbsent("mismatch", false);
		if (state.get().current.isEmpty()) {
			setTemplate("/web/freemarker/complete.ftlh");
		} else {
			model.put("remaining", "" + state.get().countRemaining());
			model.put("question", state.get().current.get());
			setTemplate("/web/freemarker/question.ftlh");
		}
	}
}
