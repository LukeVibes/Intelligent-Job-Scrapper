package edu.carleton.comp4601.model;

import com.mongodb.BasicDBObject;

public class Equivalence extends BasicDBObject {

	private static final long serialVersionUID = 5096360302068340624L;

	String id;
	String skill1;
	String skill2;
	
	public Equivalence() {
		
	}
	
	public String getId() {
		return get("_id").toString();
	}

	public void setId(String id) {
		throw new RuntimeException("Do not reset an id!");
	}

	public String getSkill1() {
		return get("skill1").toString();
	}

	public void setSkill1(String url) {
		put("skill1", url);
	}
	
	public String getSkill2() {
		return get("skill2").toString();
	}

	public void setSkill2(String url) {
		put("skill2", url);
	}


}
