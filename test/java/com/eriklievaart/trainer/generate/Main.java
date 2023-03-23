package com.eriklievaart.trainer.generate;

import java.io.File;

import com.eriklievaart.toolkit.ant.api.AntProperties;
import com.eriklievaart.toolkit.io.api.FileTool;
import com.eriklievaart.toolkit.io.api.JvmPaths;
import com.eriklievaart.toolkit.lang.api.str.StringBuilderWrapper;

public class Main {

	public static void main(String[] args) {
		File questions = new File(JvmPaths.getJarDirOrClassDir(Main.class), "web/questions");
		File index = new File(AntProperties.getGenerateDir().getDirectory(), "/web/questions/index.txt");

		StringBuilderWrapper builder = new StringBuilderWrapper();
		for (File file : questions.listFiles()) {
			if (file.isFile()) {
				builder.appendLine(file.getName().replaceFirst(".txt$", ""));
			}
		}
		FileTool.writeStringToFile(builder.toString(), index);
		System.out.println("index generated in " + index);
	}
}
