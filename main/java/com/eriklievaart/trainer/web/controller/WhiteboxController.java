package com.eriklievaart.trainer.web.controller;

import java.util.List;

import com.eriklievaart.jl.core.api.Bean;
import com.eriklievaart.jl.core.api.Parameters;
import com.eriklievaart.jl.core.api.page.AbstractTemplateController;
import com.eriklievaart.trainer.web.io.QuestionLoader;

public class WhiteboxController extends AbstractTemplateController {

	private List<String> index;
	private QuestionLoader loader;

	@Bean
	private Parameters parameters;

	public WhiteboxController(List<String> index, QuestionLoader loader) {
		this.index = index;
		this.loader = loader;
	}

	@Override
	public void invoke() throws Exception {
		model.put("courses", index);
		setTemplate("/web/freemarker/whitebox.ftlh");

		parameters.getString("c", course -> {
			State state = new State(loader, course);
			model.put("whitebox", state.createWhitebox());
			model.put("redundant", state.countRedundantProgress());
		});
	}
}
