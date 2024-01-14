package com.eriklievaart.trainer.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.eriklievaart.jl.core.api.Bean;
import com.eriklievaart.jl.core.api.Parameters;
import com.eriklievaart.jl.core.api.exception.ExternalRedirectException;
import com.eriklievaart.jl.core.api.page.AbstractTemplateController;
import com.eriklievaart.toolkit.lang.api.collection.FromCollection;
import com.eriklievaart.trainer.web.io.CourseSelectionIO;
import com.eriklievaart.trainer.web.io.QuestionLoader;

public class SelectQuestioneerController extends AbstractTemplateController {

	private CourseSelectionIO io;
	private QuestionLoader loader;
	private List<String> questioneers;

	@Bean
	private HttpSession session;
	@Bean
	private Parameters parameters;

	public SelectQuestioneerController(List<String> index, QuestionLoader loader, CourseSelectionIO io) {
		this.questioneers = index;
		this.loader = loader;
		this.io = io;
	}

	@Override
	public void invoke() throws Exception {
		if (parameters.contains("q")) {
			List<String> selection = parameters.getStrings("q");
			State state = new State(loader, selection);
			SessionAttributes.setSelectedCourses(session, state);
			io.storeSelection(selection);
			throw new ExternalRedirectException("/web/practice");

		} else {
			State state = SessionAttributes.getSelectedCourses(session);
			model.put("selected", state == null ? io.loadLastSelection() : FromCollection.toSet(state.getCourses()));
			model.put("questioneers", questioneers);
			setTemplate("/web/freemarker/select-courses.ftlh");
		}
	}
}
