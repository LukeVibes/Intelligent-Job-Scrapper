package edu.carleton.comp4601.utility;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class DatabaseSingleton {
	private static DatabaseSingleton instance;	
    private static MongoClient dbClient;
    private static DB database;
    private static String db_url = "localhost";
    private static Integer db_port = 27017;
    private static String db_name = "project";

	public DatabaseSingleton() throws UnknownHostException {
		dbClient = new MongoClient(db_url, db_port);
		database = dbClient.getDB(db_name);		
	}
	
	public synchronized static void setInstance(DatabaseSingleton instance) {
		DatabaseSingleton.instance = instance;
	}
	
	public synchronized static DatabaseSingleton getInstance() throws UnknownHostException {
		if (instance == null)
			instance = new DatabaseSingleton();
		return instance;
	}
		
	public synchronized DBCollection getCollection(String collectionName) {
		DBCollection collection;
		boolean exists = database.collectionExists(collectionName);
		if(!exists) {
			database.createCollection(collectionName, null);
		}
		
		collection = database.getCollection(collectionName);
		return collection;
	}
		
	public synchronized void addToCollection(String collectionName, BasicDBObject objectToAdd) {
		DBCollection coll = getCollection(collectionName);
		
		//Step ONE: see if it object already exists
		 BasicDBObject query = new BasicDBObject().append("skill", objectToAdd.get("skill"));
		 BasicDBObject result = findObject(collectionName, query);
		
		//IT EXISTS: Update
		if(result != null) { 
			BasicDBObject searchQuery = new BasicDBObject().append("skill", objectToAdd.getString("skill"));
			coll.update(searchQuery, objectToAdd);
		 
		 }
		 
		//IT IS NEW: Add
		 else{
			 getCollection(collectionName).insert(objectToAdd); 
		 }
		 
       
		
		
	}
	
	public synchronized BasicDBObject findObject(String collectionName, BasicDBObject document){
		BasicDBObject obj = (BasicDBObject)getCollection(collectionName).findOne(document);
		if(obj == null)
			return null;
		return obj;
	}
	
	
	public synchronized void closeConnection() {
		dbClient.close();
	}

}