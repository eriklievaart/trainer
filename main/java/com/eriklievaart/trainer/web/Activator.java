package com.eriklievaart.trainer.web;

import org.osgi.framework.BundleContext;

import com.eriklievaart.jl.core.api.osgi.LightningActivator;
import com.eriklievaart.jl.core.api.page.PageSecurity;

public class Activator extends LightningActivator {

	private State state = new State();

	public Activator() {
		super("web");
	}

	@Override
	protected void init(BundleContext context) throws Exception {
		addTemplateSource();

		addPageService(builder -> {
			builder.newRoute("root").mapGet("", () -> new QuestionController(state));
			builder.setSecurity(new PageSecurity((route, ctx) -> true));
		});
	}
}
