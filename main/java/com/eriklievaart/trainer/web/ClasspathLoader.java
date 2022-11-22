package com.eriklievaart.trainer.web;

import java.io.InputStream;

import com.eriklievaart.toolkit.io.api.ResourceTool;
import com.eriklievaart.trainer.web.loader.QuestionLoader;

public class ClasspathLoader implements QuestionLoader {

	@Override
	public InputStream getInputStream() {
		return ResourceTool.getInputStream(Questions.class, "/web/questions.txt");
	}

	public boolean isModified() {
		return false;
	}
}
