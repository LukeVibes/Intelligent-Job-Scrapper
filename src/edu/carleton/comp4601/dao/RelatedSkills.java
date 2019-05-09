package edu.carleton.comp4601.dao;

import java.util.LinkedHashMap;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import edu.carleton.comp4601.utility.DatabaseSingleton;

public class RelatedSkills {
	private static RelatedSkills instance;

	public static RelatedSkills getInstance() {
		if (instance == null)
			instance = new RelatedSkills();
		return instance;
	}

	public RelatedSkills() {

	}

	public Map<String, Double> getRelatedSkills(String id) {
		System.out.println(id);
		String skill = Skills.getInstance().getSkill(id);
		Map<String, Double> relatedSkills = new LinkedHashMap<String, Double>();
		try {
			BasicDBObject basicdbobj = new BasicDBObject();
			basicdbobj.append("skill", skill);
			DBObject obj = DatabaseSingleton.getInstance().findObject("relatedSkills", basicdbobj);
			if(obj.containsField("associates")) {
				String innerObj = (String) obj.get("associates");
				innerObj = innerObj.substring(1, innerObj.length()-2);
				String[] arrayInnerObj = innerObj.split(",");
				for(String nameValuePair : arrayInnerObj) {
					String name = nameValuePair.substring(0, nameValuePair.indexOf("="));
					Double value = Double.valueOf(nameValuePair.substring(nameValuePair.indexOf("=")+1));
					relatedSkills.put(name, value);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return relatedSkills;
	}
	
}
