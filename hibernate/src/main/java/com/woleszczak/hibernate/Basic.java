package com.woleszczak.hibernate;

import java.util.Date;

import org.hibernate.Session;

import com.woleszczak.hibernate.model.Employee;

/**
 * Hello world!
 *
 */
public class Basic 
{
    public static void main( String[] args )
    {
		Employee emp = new Employee();
		emp.setName("Wojtek");
		emp.setRole("CTO");
		emp.setInsertTime(new Date());
		
		//Get Session
		Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		//start transaction
		session.beginTransaction();
		//Save the Model object
		session.save(emp);
		//Commit transaction
		session.getTransaction().commit();
		System.out.println("Employee ID="+emp.getId());
		
		//terminate session factory, otherwise program won't end
		HibernateUtil.getSessionAnnotationFactory().close();
    }
}
