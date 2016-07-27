package com.woleszczak.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.woleszczak.hibernate.model.Employee;

public class FirstLevelCache {

	public static void main(String[] args) throws InterruptedException {
		
		SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		
		//Get employee with id=1
		Employee emp = (Employee) session.load(Employee.class, new Integer(20));
		printData(emp,1);
		
		//waiting for sometime to change the data in backend
		Thread.sleep(10000);
		
		//Fetch same data again, check logs that no query fired
		Employee emp1 = (Employee) session.load(Employee.class, new Integer(20));
		printData(emp1,2);
		
		//Create new session
		Session newSession = sessionFactory.openSession();
		//Get employee with id=20, notice the logs for query
		Employee emp2 = (Employee) newSession.load(Employee.class, new Integer(20));
		printData(emp2,3);
		
		//START: evict example to remove specific object from hibernate first level cache
		//Get employee with id=2, first time hence query in logs
		Employee emp3 = (Employee) session.load(Employee.class, new Integer(22));
		printData(emp3,4);
		
		//evict the employee object with id=20
		session.evict(emp);
		System.out.println("Session Contains Employee with id=1=20?"+session.contains(emp));

		//since object is removed from first level cache, you will see query in logs
		Employee emp4 = (Employee) session.load(Employee.class, new Integer(20));
		printData(emp4,5);
		
		//this object is still present, so you won't see query in logs
		Employee emp5 = (Employee) session.load(Employee.class, new Integer(22));
		printData(emp5,6);
		//END: evict example
		
		//START: clear example to remove everything from first level cache
		session.clear();
		Employee emp6 = (Employee) session.load(Employee.class, new Integer(20));
		printData(emp6,7);
		Employee emp7 = (Employee) session.load(Employee.class, new Integer(22));
		printData(emp7,8);
		
		System.out.println("Session Contains Employee with id=2?"+session.contains(emp7));
		
		tx.commit();
		sessionFactory.close();
	}

	private static void printData(Employee emp, int count) {
		System.out.println(count+":: Name="+emp.getName()+", Role="+emp.getRole());
	}

}
