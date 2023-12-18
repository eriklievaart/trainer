package com.eriklievaart.trainer.web.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Optional;

import com.eriklievaart.toolkit.io.api.ResourceTool;
import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.io.api.UrlTool;
import com.eriklievaart.toolkit.logging.api.LogTemplate;
import com.eriklievaart.trainer.web.Questions;

public class ImageLoader {
	private LogTemplate log = new LogTemplate(getClass());

	private Optional<File> override;

	public ImageLoader(Optional<File> override) {
		this.override = override;
	}

	public InputStream getInputStream(String resource) {
		if (!override.isPresent()) {
			return ResourceTool.getInputStream(Questions.class, UrlTool.append("/web/questions/", resource));
		}
		try {
			File file = new File(override.get(), resource);
			log.trace("loading hot: $", file);
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeIOException(e);
		}
	}
}
