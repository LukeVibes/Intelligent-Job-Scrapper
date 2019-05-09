package edu.carleton.comp4601.dao;

import java.util.HashMap;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import edu.carleton.comp4601.model.Equivalence;
import edu.carleton.comp4601.utility.DatabaseSingleton;

public class Equivalencies {
	private static Equivalencies instance;
	
	public static Equivalencies getInstance() {
		if (instance == null)
			instance = new Equivalencies();
		return instance;
	}

	public Equivalencies() {

	}

	public HashMap<String, String> getEquivalencies() {
		HashMap<String, String> equivs = new HashMap<String, String>();
		try {
			DBCollection equivsCollection = DatabaseSingleton.getInstance().getCollection("equivalence");
			equivsCollection.setObjectClass(Equivalence.class);
			DBCursor equivEntries = equivsCollection.find();
			try {
				while(equivEntries.hasNext()) {
					Equivalence equivEntry = (Equivalence) equivEntries.next();
					equivs.put(equivEntry.getSkill1(), equivEntry.getSkill2());
				}
			} finally {
				equivEntries.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return equivs;
	}


}
