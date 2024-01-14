package com.eriklievaart.trainer.web.io;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.eriklievaart.toolkit.io.api.FileTool;
import com.eriklievaart.toolkit.io.api.LineFilter;
import com.eriklievaart.toolkit.lang.api.collection.FromCollection;

public class CourseSelectionIO {

	private File data;

	public CourseSelectionIO(File root) {
		data = new File(root, "data");
	}

	public Set<String> loadLastSelection() {
		File file = getFile();
		if (file.isFile()) {
			return FromCollection.toSet(new LineFilter(file).dropBlank().dropHash().list());
		} else {
			return new HashSet<>();
		}
	}

	public void storeSelection(List<String> selection) {
		FileTool.writeLines(getFile(), selection);
	}

	public File getFile() {
		return new File(data, "selected-courses.txt");
	}
}
