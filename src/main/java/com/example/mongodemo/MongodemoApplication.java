package com.example.mongodemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

@SpringBootApplication
@RestController
public class MongodemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongodemoApplication.class, args);
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping("/run")
	public static void mongoOperations() {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		DB database = mongoClient.getDB("myMongoDb");
		mongoClient.getDatabaseNames().forEach(System.out::println);
		database.createCollection("customers", null);
		database.getCollectionNames().forEach(System.out::println);
		
		DBCollection collection = database.getCollection("customers");
		BasicDBObject document = new BasicDBObject();
		document.put("name", "Ravi");
		document.put("company", "KRT Group");
		collection.insert(document);
		
		BasicDBObject query = new BasicDBObject();
		query.put("name", "Ravi");
		 
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("name", "John");
		 
		BasicDBObject updateObject = new BasicDBObject();
		updateObject.put("$set", newDocument);
		 
		collection.update(query, updateObject);
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", "John");
		DBCursor cursor = collection.find(searchQuery);
		 
		while (cursor.hasNext()) {
		    System.out.println(cursor.next());
		}
		
		searchQuery = new BasicDBObject();
		searchQuery.put("name", "John");
		 
		collection.remove(searchQuery);
	}
}
