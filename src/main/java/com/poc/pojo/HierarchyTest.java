package com.poc.pojo;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "heirarchy")
public class HierarchyTest {

	String id;
	List<?> children;

	public HierarchyTest() {

	}

	public HierarchyTest(String id, List<?> children) {

		this.id = id;
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<?> getChildren() {
		return children;
	}

	public void setChildren(List<?> children) {
		this.children = children;
	}

}
