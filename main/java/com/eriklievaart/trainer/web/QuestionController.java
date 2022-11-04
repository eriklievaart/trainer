package com.eriklievaart.trainer.web;

import com.eriklievaart.javalightning.bundle.api.Bean;
import com.eriklievaart.javalightning.bundle.api.Parameters;
import com.eriklievaart.javalightning.bundle.api.RequestContext;
import com.eriklievaart.javalightning.bundle.api.page.AbstractTemplateController;

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
		parameters.getOptional("answer").ifPresent(answer -> {
			Question removed = state.questions.remove(0);
			if (!removed.getAnswer().equals(answer)) {
				model.put("previous", removed);
				model.put("answer", answer);
				state.questions.add(removed);
			}
		});
		render(state.questions.get(0));
	}

	private void render(Question question) {
		model.put("question", question);
		setTemplate("/web/freemarker/question.ftlh");
	}
}
