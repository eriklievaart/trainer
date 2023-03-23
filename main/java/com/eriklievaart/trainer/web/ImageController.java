package com.eriklievaart.trainer.web;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import com.eriklievaart.jl.core.api.Bean;
import com.eriklievaart.jl.core.api.ResponseBuilder;
import com.eriklievaart.jl.core.api.page.PageController;
import com.eriklievaart.jl.core.api.render.InputStreamRenderer;
import com.eriklievaart.toolkit.io.api.ResourceTool;
import com.eriklievaart.toolkit.io.api.UrlTool;

public class ImageController implements PageController {

	@Bean
	private HttpServletRequest request;

	@Override
	public void invoke(ResponseBuilder response) throws Exception {
		String image = UrlTool.getTail(request.getRequestURI()).replaceAll("%20", " ");
		String resource = UrlTool.append("/web/questions/", image);
		InputStream is = ResourceTool.getInputStream(getClass(), resource);
		response.setRenderer(new InputStreamRenderer(is));
	}
}
