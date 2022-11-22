package com.eriklievaart.trainer.web.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.eriklievaart.toolkit.io.api.RuntimeIOException;

public class HotLoader implements QuestionLoader {

	private final File file;

	public HotLoader(File file) {
		this.file = file;
	}

	@Override
	public InputStream getInputStream() {
		try {
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
