package com.eriklievaart.trainer.boot;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.eriklievaart.toolkit.io.api.FileTool;
import com.eriklievaart.toolkit.io.api.UrlTool;

public class Verkeersborden {

	public static void main(String[] args) {
		File file = new File("/home/eazy/Development/git/trainer/verkeersborden/verkeersborden.html");
		String raw = FileTool.toString(file);
		Document document = Jsoup.parse(raw);
		Elements rows = document.select(".exercise-overview a");
		for (Element row : rows) {
			String description = getDeescription(row.attr("href"));
			String img = UrlTool.getName(row.select("img").attr("src"));
			System.out.println("[" + img + "] " + description);
		}
	}

	private static String getDeescription(String link) {
		String tail = UrlTool.getName(link).replaceFirst("/$", "");
		return tail.replaceFirst("(type-)?[^-]++-", "welk bord is dit? $0 | ").replace('-', ' ');
	}
}
