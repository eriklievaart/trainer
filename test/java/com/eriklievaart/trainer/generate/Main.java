package com.eriklievaart.trainer.generate;

import java.io.File;

import com.eriklievaart.toolkit.io.api.FileTool;
import com.eriklievaart.toolkit.io.api.JvmPaths;
import com.eriklievaart.toolkit.lang.api.str.StringBuilderWrapper;

public class Main {

	public static void main(String[] args) {
		File root = new File(JvmPaths.getJarDirOrClassDir(Main.class), "web/questions");
		File index = new File(root, "index.txt");

		StringBuilderWrapper builder = new StringBuilderWrapper();
		for (File file : root.listFiles()) {
			builder.appendLine(file.getName().replaceFirst(".txt$", ""));
		}
		FileTool.writeStringToFile(builder.toString(), index);
		System.out.println("index generated in " + index);
	}
}
