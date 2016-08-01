package com.woleszczak.hibernate.spring;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.woleszczak.hibernate.model.Person;
import com.woleszczak.hibernate.spring.dao.PersonDAO;

public class SpringHibernateMain {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring4.xml");
		
		PersonDAO personDAO = context.getBean(PersonDAO.class);
		
		Person person = new Person();
		person.setName("Wojtek"); person.setCountry("Poland");
		
		personDAO.addPerson(person);
		
		System.out.println("Person::"+person);
		
		List<Person> list = personDAO.listPersons();
		
		for(Person p : list){
			System.out.println("Person List::"+p);
		}
		//close resources
		context.close();	
	}
}
