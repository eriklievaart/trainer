package com.eriklievaart.trainer.web.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.eriklievaart.toolkit.io.api.RuntimeIOException;

public class HotLoader implements QuestionLoader {

	private final File directory;

	public HotLoader(File file) {
		this.directory = file;
	}

	@Override
	public InputStream getInputStream(String course) {
		try {
			return new FileInputStream(new File(directory, course + ".txt"));
		} catch (FileNotFoundException e) {
			throw new RuntimeIOException(e);
		}
	}

	@Override
	public boolean isModified() {
		return true;
	}
}
