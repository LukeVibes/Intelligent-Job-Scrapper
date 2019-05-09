package edu.carleton.comp4601.model;

import java.util.HashMap;

import com.mongodb.BasicDBObject;

public class Pages {
	private static Pages instance;
	HashMap<String, PageSummary> pages;

	public synchronized void reset(){
		 pages = new HashMap<String, PageSummary>();
	}
	
	public synchronized static Pages getInstance() {
		if (instance == null)
			instance = new Pages();
		return instance;
	}
	
	public Pages() {
		pages = new HashMap<String, PageSummary>();
	}
	
	public synchronized void addPage(String URL, String Location) {
		pages.put(URL, new PageSummary(Location));
	}
	
	public synchronized void addSkill(String URL, String skill) {
		pages.get(URL).addSkill(skill);
	}
	
	public synchronized HashMap<String, Boolean> getSkills(String URL) {
		return pages.get(URL).getSkills();
	}
	
	public synchronized HashMap<String, PageSummary> getPages() {
		return pages;
	}
}
