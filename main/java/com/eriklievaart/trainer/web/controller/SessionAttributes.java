package com.eriklievaart.trainer.web.controller;

import javax.servlet.http.HttpSession;

public class SessionAttributes {

	public static State getSelectedCourses(HttpSession session) {
		return (State) session.getAttribute("questioneers");
	}

	public static void setSelectedCourses(HttpSession session, State values) {
		session.setAttribute("questioneers", values);
	}
}
