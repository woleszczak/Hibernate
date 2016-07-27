package com.woleszczak.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.woleszczak.hibernate.model.Employee;

public class Log4jExample {

    static{
        System.out.println("Before log4j configuration");
        DOMConfigurator.configure("src/main/resources/log4j.xml");
        System.out.println("After log4j configuration");
    }
     
    private static Logger logger = Logger.getLogger(Log4jExample.class);
     
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
         
        //Prep work
        SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        Session session = sessionFactory.getCurrentSession();
         
        //Get All Employees
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Employee");
        List<Employee> empList = query.list();
        for(Employee emp : empList){
            logger.info("List of Employees::"+emp.getId()+","+emp.getName());
        }
         
        tx.commit();
        sessionFactory.close();
        logger.info("DONE");
    }

}
