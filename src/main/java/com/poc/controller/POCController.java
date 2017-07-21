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

@RestController
public class POCController {

	@Autowired
	HierarchyRepository hierarchyRepository;

	@CrossOrigin
	@RequestMapping("/readAndStore")
	public String readAndStore(HttpSession httpSession) {

		System.out.println("Entering readJSON");
		String msg = "";
		try {

			String filepath="D:\\PreetamProj\\EclipseProj\\POC\\src\\main\\java\\com\\poc\\controller\\HierarchyTest.json";
			
			File jsonFile = new File(filepath);

			HierarchyTest hierarchyTest = new HierarchyTest();
			ObjectMapper mapper = new ObjectMapper();

			hierarchyTest = mapper.readValue(jsonFile, HierarchyTest.class);

			System.out.println("Id is : " + hierarchyTest.getId());

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

	@CrossOrigin
	@RequestMapping(value = "/showTreeMap", produces = "application/json")
	public HierarchyTest showTreeMap() {
		System.out.println("Entering showTreeMap");
		return hierarchyRepository.findAll().get(0);

	}

}
