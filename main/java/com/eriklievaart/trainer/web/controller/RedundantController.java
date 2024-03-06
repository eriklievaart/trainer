package com.eriklievaart.trainer.web.controller;

import com.eriklievaart.jl.core.api.Bean;
import com.eriklievaart.jl.core.api.Parameters;
import com.eriklievaart.jl.core.api.ResponseBuilder;
import com.eriklievaart.jl.core.api.exception.ExternalRedirectException;
import com.eriklievaart.jl.core.api.page.PageController;
import com.eriklievaart.trainer.web.io.QuestionLoader;

public class RedundantController implements PageController {

	private QuestionLoader loader;

	@Bean
	private Parameters parameters;

	public RedundantController(QuestionLoader loader) {
		this.loader = loader;
	}

	@Override
	public void invoke(ResponseBuilder response) throws Exception {
		String course = parameters.getString("course");
		new State(loader, course).deleteRedundantProgress();
		throw new ExternalRedirectException("/web/whitebox?c=" + course);
	}
}
