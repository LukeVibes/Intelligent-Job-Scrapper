package edu.carleton.comp4601.model;

import java.util.HashMap;
import java.util.HashSet;

import edu.carleton.comp4601.dao.Skills;

public class PageSummary {
	String Location;
	HashMap<String, Boolean> skills = new HashMap<String, Boolean>();
	private static HashSet<String> SKILLS = Skills.getInstance().getSkills();
	
	public PageSummary(String Location) {
		this.Location = Location;
		for(String skill : SKILLS) {
			skills.put(skill, false);
		}
	}
	
	public void addSkill(String skill) {
		skills.put(skill, true);
	}
	
	public HashMap<String, Boolean> getSkills() {
		return skills;
	}
	
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
		
}
