package edu.carleton.comp4601.utility;

import java.util.HashSet;
import java.util.Set;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import edu.carleton.comp4601.dao.Skills;
import edu.carleton.comp4601.model.Skill;

public class SplitSkills {

	public static void main(String[] args) {
		//		int size = Skills.getInstance().getSkills().size();
		//		int count = 0;
		//		Set<String> firstHalf = new HashSet<String>();
		//		Set<String> secondHalf = new HashSet<String>();
		//		for(String skill : Skills.getInstance().getSkills()) {
		//			if(count<Math.floor(size/2)) {
		//				firstHalf.add(skill);
		//			} else {
		//				secondHalf.add(skill);
		//			}
		//			count++;
		//		}
		//		
		//		System.out.println("First half:");
		//		for(String skill : firstHalf) {
		//			System.out.println(skill);
		//		}
		//		
		//		System.out.println("");
		//		System.out.println("Second half:");
		//		for(String skill : secondHalf) {
		//			System.out.println(skill);
		//		}
		Boolean found;
		HashSet<String> missingSkills = new HashSet<String>();
		for(String skill : Skills.getInstance().getSkills()) {
			found = false;
			try {
				DBCollection relatedSkills = DatabaseSingleton.getInstance().getCollection("relatedSkills");
				DBCursor relatedSkillEntries = relatedSkills.find();
				try {
					while(relatedSkillEntries.hasNext()) {
						DBObject relatedSkillEntry = relatedSkillEntries.next();
						String relatedSkill = (String) relatedSkillEntry.get("skill");
						if(relatedSkill.equals(skill)) 
							found = true;
					}
				} finally {
					relatedSkillEntries.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			if(!found)
				missingSkills.add(skill);
		}
		for(String missing : missingSkills) {
			System.out.println("missing skill: "+missing);
		}
	}

}
