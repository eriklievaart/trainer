package com.eriklievaart.trainer.web.controller;

import java.util.function.Supplier;

import com.eriklievaart.toolkit.lang.api.collection.LazyMap;
import com.eriklievaart.trainer.web.io.QuestionLoader;

public class StateSuppliers {

	private LazyMap<String, State> states;

	public StateSuppliers(QuestionLoader loader) {
		states = new LazyMap<>(c -> new State(loader, c));
	}

	public Supplier<State> getSupplier(String course) {
		return () -> states.get(course);
	}
}
