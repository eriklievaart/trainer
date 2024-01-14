package com.eriklievaart.trainer.web;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.osgi.framework.BundleContext;

import com.eriklievaart.jl.core.api.osgi.LightningActivator;
import com.eriklievaart.jl.core.api.page.PageSecurity;
import com.eriklievaart.osgi.toolkit.api.ContextWrapper;
import com.eriklievaart.toolkit.io.api.CheckFile;
import com.eriklievaart.toolkit.io.api.ResourceTool;
import com.eriklievaart.toolkit.lang.api.collection.ListTool;
import com.eriklievaart.toolkit.logging.api.LogTemplate;
import com.eriklievaart.trainer.web.controller.ImageController;
import com.eriklievaart.trainer.web.controller.IndexController;
import com.eriklievaart.trainer.web.controller.SelectQuestioneerController;
import com.eriklievaart.trainer.web.controller.PracticeController;
import com.eriklievaart.trainer.web.io.CourseSelectionIO;
import com.eriklievaart.trainer.web.io.QuestionLoader;

public class Activator extends LightningActivator {

	private static final String QUESTION_DIR = "com.eriklievaart.trainer.web.question.dir";

	private LogTemplate log = new LogTemplate(getClass());

	@Override
	protected void init(BundleContext context) throws Exception {
		addTemplateSource();
		createRoutes();
	}

	private void createRoutes() {
		Optional<File> override = getOverride();
		QuestionLoader loader = new QuestionLoader(override);
		CourseSelectionIO io = new CourseSelectionIO(getContextWrapper().getBundleParentDir());
		List<String> index = Collections.unmodifiableList(createIndex(override));

		addPageService(builder -> {
			builder.newRoute("root").mapGet("", () -> new SelectQuestioneerController(index, loader, io));
			builder.newIdentityRouteGet("list", () -> new IndexController(index));
			builder.newIdentityRouteGet("practice", () -> new PracticeController());
			builder.newRoute("images").mapGet("images/*", () -> new ImageController(override));
			builder.setSecurity(new PageSecurity((route, ctx) -> true));
		});
	}

	private List<String> createIndex(Optional<File> override) {
		if (override.isPresent()) {
			List<String> list = ListTool.filterAndMap(override.get().listFiles(), f -> f.isFile(), this::getBaseName);
			return ListTool.sortedCopy(list);
		}
		return ResourceTool.getLines(getClass(), "/web/questions/index.txt");
	}

	private String getBaseName(File file) {
		return file.getName().replaceFirst("[.]txt$", "");
	}

	private Optional<File> getOverride() {
		ContextWrapper context = getContextWrapper(); // required for OSGI

		if (context.hasProperty(QUESTION_DIR)) {
			File directory = new File(context.getPropertyString(QUESTION_DIR, null));
			log.info("hot loading questions from: " + directory);
			CheckFile.isDirectory(directory);
			return Optional.of(directory);
		}
		log.info("using classpath loader for questions; set property % to change", QUESTION_DIR);
		return Optional.empty();
	}
}
