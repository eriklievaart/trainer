package com.eriklievaart.trainer.web.controller;

import javax.servlet.http.HttpSession;

import com.eriklievaart.jl.core.api.Bean;
import com.eriklievaart.jl.core.api.Parameters;
import com.eriklievaart.jl.core.api.exception.ExternalRedirectException;
import com.eriklievaart.jl.core.api.page.AbstractTemplateController;
import com.eriklievaart.toolkit.logging.api.LogTemplate;

public class PracticeController extends AbstractTemplateController {
	private LogTemplate log = new LogTemplate(getClass());

	@Bean
	private HttpSession session;
	@Bean
	protected Parameters parameters;

	@Override
	public void invoke() throws Exception {
		State state = SessionAttributes.getSelectedCourses(session);
		if (state == null) {
			log.warn("state does not exist, possible restart, redirecting");
			throw new ExternalRedirectException("/");
		}
		processAnswer(state);
		render(state);
	}

	protected void processAnswer(State state) {
		parameters.getOptional("answer").ifPresent(answer -> {
			Question query = state.current.get();

			if (!query.getHash().equals(parameters.getString("hash"))) {
				model.put("mismatch", true);
			} else if (query.isValidAnswer(answer)) {
				state.correct();
			} else {
				state.incorrect(SessionAttributes.getMinimumDelay(session));
				model.put("previous", query);
				model.put("answer", answer);
			}
		});
		state.reloadIfModified();
	}

	protected void render(State state) {
		model.putIfAbsent("mismatch", false);

		if (state.current.isEmpty()) {
			model.put("timestamp", "" + state.getWaitTimestamp());
			setTemplate("/web/freemarker/complete.ftlh");

		} else {
			model.put("question", state.current.get());
			model.put("remaining", "" + state.countRemaining());
			setTemplate("/web/freemarker/question.ftlh");
		}
	}
}
