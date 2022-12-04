package com.eriklievaart.trainer.web.loader;

import java.io.InputStream;

public interface QuestionLoader {

	public InputStream getInputStream(String course);

	public boolean isModified();
}
