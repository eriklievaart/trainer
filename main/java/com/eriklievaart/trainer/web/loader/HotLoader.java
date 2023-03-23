package com.eriklievaart.trainer.web.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.logging.api.LogTemplate;

public class HotLoader implements QuestionLoader {
	private LogTemplate log = new LogTemplate(getClass());

	private final File directory;

	public HotLoader(File file) {
		this.directory = file;
	}

	@Override
	public InputStream getInputStream(String course) {
		try {
			File file = new File(directory, course + ".txt");
			log.trace("loading hot: $", file);
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeIOException(e);
		}
	}

	@Override
	public boolean isModified() {
		return true;
	}
}
