package com.woleszczak.hibernate;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import com.woleszczak.hibernate.model.Employee;

public class NativeQuery {

	public static void main(String[] args) {
		// Prep work
		SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
		Session session = sessionFactory.getCurrentSession();

		// Get All Employees
		Transaction tx = session.beginTransaction();
		SQLQuery query = session.createSQLQuery("select id, name, role from Employee");
		List<Object[]> rows = query.list();
		for(Object[] row : rows){
			Employee emp = new Employee();
			emp.setId(Integer.parseInt(row[0].toString()));
			emp.setName(row[1].toString());
			emp.setRole(row[2].toString());
			System.out.println(emp);
		}
		
		query = session.createSQLQuery("select id, name, role from Employee")
				.addScalar("id", new IntegerType())
				.addScalar("name", new StringType())
				.addScalar("role", new StringType());
		
		query = session
				.createSQLQuery("select id, name, role from Employee where id = ?");
		List<Object[]> empData = query.setInteger(0, 21).list();
		for (Object[] row : empData) {
			Employee emp = new Employee();
			emp.setId(Integer.parseInt(row[0].toString()));
			emp.setName(row[1].toString());
			emp.setRole(row[2].toString());
			System.out.println(emp);
		}
	}

}
