package com.woleszczak.hibernate;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.Session;

import com.woleszczak.hibernate.model.Employee;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		
		Employee emp = new Employee();
		emp.setName("Vel");
		emp.setRole("Test");
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
		
		
		System.out.println("\nSimple field level validation example");
		emp = new Employee();
		emp.setName("Raf");
		emp.setRole("CEO");
		emp.setInsertTime(new Date());
		Set<ConstraintViolation<Employee>> validationErrors = validator.validate(emp);
		
		if(!validationErrors.isEmpty()){
			for(ConstraintViolation<Employee> error : validationErrors){
				System.out.println(error.getMessageTemplate()+"::"+error.getPropertyPath()+"::"+error.getMessage());
				
			}
		}
    }
}
