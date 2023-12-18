package com.eriklievaart.trainer.web.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Optional;

import com.eriklievaart.toolkit.io.api.ResourceTool;
import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.logging.api.LogTemplate;
import com.eriklievaart.trainer.web.Questions;

public class QuestionLoader {
	private LogTemplate log = new LogTemplate(getClass());

	private Optional<File> override;

	public QuestionLoader(Optional<File> override) {
		this.override = override;
	}

	public InputStream getInputStream(String course) {
		if (!override.isPresent()) {
			return ResourceTool.getInputStream(Questions.class, Str.sub("/web/questions/$.txt", course));
		}
		try {
			File file = new File(override.get(), course + ".txt");
			log.trace("loading hot: $", file);
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeIOException(e);
		}
	}

	public boolean isModified() {
		return override.isPresent();
	}
}
