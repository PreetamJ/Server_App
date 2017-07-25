package com.poc.controller;

import java.io.File;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.poc.pojo.HierarchyTest;

import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.mongodb.Mongo;

@RestController
public class POCController {
	String classID;

	@Autowired
	HierarchyRepository hierarchyRepository;

	/**
	 * This method reads and stored Hierarchy data into DB Returns message
	 * whether JSON stored successfully or not. This method can be accessed
	 * from client application with url "/readAndStore"
	 * 
	 * @param httpSession
	 * @return msg
	 */
	@CrossOrigin
	@RequestMapping("/readAndStore")
	public String readAndStore(HttpSession httpSession) {

		System.out.println("Entering readJSON");
		String msg = "";
		try {

			String filepath = "D:\\PreetamProj\\All Projects\\Server_App\\src\\main\\java\\com\\poc\\controller\\HierarchyTest.json";

			File jsonFile = new File(filepath);

			HierarchyTest hierarchyTest = new HierarchyTest();
			ObjectMapper mapper = new ObjectMapper();

			hierarchyTest = mapper.readValue(jsonFile, HierarchyTest.class);

			System.out.println("Id is : " + hierarchyTest.getId());

			classID = hierarchyTest.getId();
			
			hierarchyRepository.save(hierarchyTest);

			msg = "JSON file HierarchyTest has been read successfully !!!";

		} catch (JsonParseException e) {
			msg = "Error while parsing HierarchyTest.json ";
			e.printStackTrace();
		} catch (JsonMappingException e) {
			msg = "Error while mapping HierarchyTest.json ";
			e.printStackTrace();
		} catch (IOException e) {
			msg = "Error while reading HierarchyTest.json ";
			e.printStackTrace();
		}

		return msg;
	}

	/**
	 * This method retrieves data from Hierarchy collection from MongoDB
	 * This method can be accessed from client application with url "/showTreeMap"
	 * 
	 * @return HierarchyTest record inserted
	 */
	@CrossOrigin
	@RequestMapping(value = "/showTreeMap", produces = "application/json")
	public HierarchyTest showTreeMap() {
		System.out.println("Entering showTreeMap");

		HierarchyTest p = new HierarchyTest();
		MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new Mongo(), "poc_db"));
		p = mongoOps.findById(classID, HierarchyTest.class);
		System.out.println("classId is :"+classID);

		return p;

	}

}
