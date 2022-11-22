package com.eriklievaart.trainer.web.loader;

import java.io.InputStream;

public interface QuestionLoader {

	public InputStream getInputStream();

	public boolean isModified();
}
