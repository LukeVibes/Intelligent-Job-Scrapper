package edu.carleton.comp4601.model;

import com.mongodb.BasicDBObject;

public class Skill extends BasicDBObject {
	
	private static final long serialVersionUID = -3129734460325993005L;
	
	String id;
	String skill;
	
	public Skill() {
		
	}
	
	public String getId() {
		return get("_id").toString();
	}

	public void setId(String id) {
		throw new RuntimeException("Do not reset an id!");
	}

	public String getSkill() {
		return get("skill").toString();
	}

	public void setSkill(String url) {
		put("skill", url);
	}

}
