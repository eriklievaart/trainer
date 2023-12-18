package com.eriklievaart.trainer.web;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.osgi.framework.BundleContext;

import com.eriklievaart.jl.core.api.osgi.LightningActivator;
import com.eriklievaart.jl.core.api.page.PageSecurity;
import com.eriklievaart.osgi.toolkit.api.ContextWrapper;
import com.eriklievaart.toolkit.io.api.CheckFile;
import com.eriklievaart.toolkit.io.api.ResourceTool;
import com.eriklievaart.toolkit.logging.api.LogTemplate;
import com.eriklievaart.trainer.web.loader.QuestionLoader;

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

		StateSuppliers states = new StateSuppliers(new QuestionLoader(override));
		List<String> index = ResourceTool.getLines(getClass(), "/web/questions/index.txt");
		addPageService(builder -> {
			for (String course : index) {
				builder.newRoute(course).mapGet(course, () -> new QuestionController(states.getSupplier(course)));
				builder.newRoute(course + ".img").mapGet(course + "/*", () -> new ImageController(override));
			}
			builder.newRoute("root").mapGet("", () -> new IndexController(index));
			builder.setSecurity(new PageSecurity((route, ctx) -> true));
		});
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
