package com.eriklievaart.trainer.web.controller;

import java.util.List;

import com.eriklievaart.jl.core.api.page.AbstractTemplateController;

public class IndexController extends AbstractTemplateController {

	private List<String> index;

	public IndexController(List<String> index) {
		this.index = index;
	}

	@Override
	public void invoke() throws Exception {
		model.put("index", index);
		setTemplate("/web/freemarker/index.ftlh");
	}
}
