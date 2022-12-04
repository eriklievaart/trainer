package com.eriklievaart.trainer.web.loader;

import java.io.InputStream;

import com.eriklievaart.toolkit.io.api.ResourceTool;
import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.trainer.web.Questions;

public class ClasspathLoader implements QuestionLoader {

	@Override
	public InputStream getInputStream(String course) {
		return ResourceTool.getInputStream(Questions.class, Str.sub("/web/questions/$.txt", course));
	}

	@Override
	public boolean isModified() {
		return false;
	}
}
