package com.woleszczak.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.woleszczak.hibernate.model.Employee;

public class NamedQuery {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		// Prep work
		SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();

		//HQL Named Query Example
		Query query = session.getNamedQuery("HQL_GET_ALL_Employee");
		List<Employee> empList = query.list();
		for (Employee emp : empList) {
			System.out.println("List of Employees::" + emp.getId() + ","
					+ emp.getName());
		}

		query = session.getNamedQuery("HQL_GET_Employee_BY_ID");
		query.setInteger("id", 20);
		Employee emp = (Employee) query.uniqueResult();
		System.out.println("Employee Name=" + emp.getName() + ", City="
				+ emp.getRole());
		
		//Native SQL Named Query Example
		query = session.getNamedQuery("@SQL_GET_ALL_Employee");
		List<Object[]> employeeObjArray = query.list();
		for(Object[] row : employeeObjArray){
			for(Object obj : row){
				System.out.print(obj + "::");
			}
			System.out.println("\n");
		}
		// rolling back to save the test data
		tx.commit();

		// closing hibernate resources
		sessionFactory.close();
	}

}
