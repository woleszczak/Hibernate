package com.woleszczak.hibernate.spring.dao;

import java.util.List;

import com.woleszczak.hibernate.model.Person;

public interface PersonDAO {

	public void save(Person p);
	
	public List<Person> list();
	
}