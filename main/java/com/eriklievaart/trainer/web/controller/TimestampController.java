package com.eriklievaart.trainer.web.controller;

import javax.servlet.http.HttpSession;

import com.eriklievaart.jl.core.api.Bean;
import com.eriklievaart.jl.core.api.ResponseBuilder;
import com.eriklievaart.jl.core.api.page.PageController;
import com.eriklievaart.jl.core.api.render.StringRenderer;
import com.eriklievaart.toolkit.lang.api.date.TimestampTool;

public class TimestampController implements PageController {

	@Bean
	private HttpSession session;

	@Override
	public void invoke(ResponseBuilder response) throws Exception {
		long timestamp = SessionAttributes.getSelectedCourses(session).getWaitTimestamp();
		long diff = timestamp - System.currentTimeMillis();

		if (diff < 0) {
			response.setRenderer(new StringRenderer("refresh"));
			return;
		}
		String hr = diff > 2000 ? TimestampTool.humanReadable(diff) : diff / 1000 + "s";
		response.setRenderer(new StringRenderer("next question in: " + hr));
	}
}
