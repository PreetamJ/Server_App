package com.poc.controller;

import java.io.File;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.poc.pojo.HierarchyTest;

import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.mongodb.Mongo;

@CrossOrigin
@RestController
public class POCController {
	String classID;

	@Autowired
	HierarchyRepository hierarchyRepository;

	/**
	 * This method reads and stored Hierarchy data into DB Returns message
	 * whether JSON stored successfully or not. This method can be accessed from
	 * client application with url "/readAndStore"
	 * 
	 * @param httpSession
	 * @return msg
	 */

	@RequestMapping(value = "/readAndStore", method = RequestMethod.POST)
	public String readAndStore(@RequestParam("file") MultipartFile file) {

		System.out.println("Entering readJSON");
		String msg = "";
		String sendId = "";
		try {

			if (!file.isEmpty()) {
				String jsonMessage = null;

				try {
					jsonMessage = new String(file.getBytes());
				} catch (Exception e) {
					e.printStackTrace();
				}

				ObjectMapper mapper = new ObjectMapper();
				HierarchyTest hierarchyTest = new HierarchyTest();
				hierarchyTest = mapper.readValue(jsonMessage, HierarchyTest.class);
				hierarchyRepository.save(hierarchyTest);
				sendId = hierarchyTest.getId();
			}
			msg = "JSON file HierarchyTest has been read successfully !!!";
			System.out.println("sendId: " + sendId);

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

		return sendId;
	}

	/**
	 * This method retrieves data from Hierarchy collection from MongoDB This
	 * method can be accessed from client application with url "/showTreeMap"
	 * 
	 * @return HierarchyTest record inserted
	 */
	@RequestMapping(value = "/showTreeMap", produces = "application/json")

	public HierarchyTest showTreeMap(@RequestParam("id") String id) {
		System.out.println("Entering showTreeMap: id is :" + id);

		HierarchyTest p = new HierarchyTest();
		MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new Mongo(), "poc_db"));

		p = mongoOps.findById(id, HierarchyTest.class);

		System.out.println("classId is :" + classID);

		return p;

	}

}
