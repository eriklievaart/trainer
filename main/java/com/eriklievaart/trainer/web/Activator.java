package com.eriklievaart.trainer.web;

import java.io.File;

import org.osgi.framework.BundleContext;

import com.eriklievaart.jl.core.api.osgi.LightningActivator;
import com.eriklievaart.jl.core.api.page.PageSecurity;
import com.eriklievaart.osgi.toolkit.api.ContextWrapper;
import com.eriklievaart.toolkit.logging.api.LogTemplate;
import com.eriklievaart.trainer.web.loader.HotLoader;

public class Activator extends LightningActivator {

	private static final String QUESTION_FILE = "com.eriklievaart.trainer.web.question.file";

	private LogTemplate log = new LogTemplate(getClass());
	private State state = new State();

	public Activator() {
		super("web");
	}

	@Override
	protected void init(BundleContext context) throws Exception {
		addTemplateSource();

		ContextWrapper wrapper = getContextWrapper();
		wrapper.getPropertyStringOptional(QUESTION_FILE, p -> {
			File file = new File(p);
			log.info("hot loading questions from: " + file);
			state.setQuestionLoader(new HotLoader(file));
		});
		addPageService(builder -> {
			builder.newRoute("root").mapGet("", () -> new QuestionController(state));
			builder.setSecurity(new PageSecurity((route, ctx) -> true));
		});
	}
}
