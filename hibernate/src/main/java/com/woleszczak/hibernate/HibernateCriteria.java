package com.woleszczak.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.woleszczak.hibernate.model.Employee;

public class HibernateCriteria {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// Prep work
		SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();

		//Get All Employees
		Criteria criteria = session.createCriteria(Employee.class);
		List<Employee> empList = criteria.list();
		for(Employee emp : empList){
			System.out.println("ID="+emp.getId()+", Role="+emp.getRole());
		}
		
		// Get with ID, creating new Criteria to remove all the settings
		criteria = session.createCriteria(Employee.class)
					.add(Restrictions.eq("id", new Integer(20)));
		Employee emp = (Employee) criteria.uniqueResult();
		System.out.println("Name=" + emp.getName() + ", Name="
				+ emp.getName());

		//Pagination Example
		empList = session.createCriteria(Employee.class)
					.addOrder(Order.desc("id"))
					.setFirstResult(0)
					.setMaxResults(2)
					.list();
		for(Employee emp4 : empList){
			System.out.println("Paginated Employees::"+emp4.getId()+","+emp4.getName());
		}

		//Like example
		empList = session.createCriteria(Employee.class)
				.add(Restrictions.like("name", "%oj%"))
				.list();
		for(Employee emp4 : empList){
			System.out.println("Employees having 'ole' in name::"+emp4.getName());
		}
		
		//Projections example
		long count = (Long) session.createCriteria(Employee.class)
				.setProjection(Projections.rowCount())
				.add(Restrictions.like("name", "%oj%"))
				.uniqueResult();
		System.out.println("Number of employees with 'oj' in name="+count);

		//using Projections for sum, min, max aggregation functions
		/*double sumSalary = (Double) session.createCriteria(Employee.class)
			.setProjection(Projections.sum("salary"))
			.uniqueResult();
		System.out.println("Sum of Salaries="+sumSalary);*/
		
		//Join example for selecting few columns
/*		criteria = session.createCriteria(Employee.class, "employee");
		criteria.setFetchMode("employee.address", FetchMode.JOIN);
		criteria.createAlias("employee.address", "address"); // inner join by default

		ProjectionList columns = Projections.projectionList()
						.add(Projections.property("name"))
						.add(Projections.property("address.city"));
		criteria.setProjection(columns);

		List<Object[]> list = criteria.list();
		for(Object[] arr : list){
			System.out.println(Arrays.toString(arr));
		}*/
		
		
		// Rollback transaction to avoid messing test data
		tx.commit();
		// closing hibernate resources
		sessionFactory.close();
	}

}
