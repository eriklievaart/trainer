package com.eriklievaart.trainer.web;

import java.io.File;
import java.io.InputStream;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.eriklievaart.jl.core.api.Bean;
import com.eriklievaart.jl.core.api.ResponseBuilder;
import com.eriklievaart.jl.core.api.page.PageController;
import com.eriklievaart.jl.core.api.render.InputStreamRenderer;
import com.eriklievaart.toolkit.io.api.UrlTool;
import com.eriklievaart.trainer.web.loader.ImageLoader;

public class ImageController implements PageController {

	private Optional<File> override;

	@Bean
	private HttpServletRequest request;

	public ImageController(Optional<File> override) {
		this.override = override;
	}

	@Override
	public void invoke(ResponseBuilder response) throws Exception {
		String resource = "_" + UrlTool.getTail(request.getRequestURI()).replaceAll("%20", " ");
		InputStream is = new ImageLoader(override).getInputStream(resource);
		response.setRenderer(new InputStreamRenderer(is));
	}
}
