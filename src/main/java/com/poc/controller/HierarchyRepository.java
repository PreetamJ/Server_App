package com.poc.controller;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.poc.pojo.HierarchyTest;


public interface HierarchyRepository extends MongoRepository<HierarchyTest, String> {

}
