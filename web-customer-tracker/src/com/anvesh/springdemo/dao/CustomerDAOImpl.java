package com.anvesh.springdemo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.anvesh.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {
	
	// need to inject the session factory
	@Autowired
	public SessionFactory sessionFactory;
	
	
	@Override
	public List<Customer> getCustomers() {
		
		
		//get the session instance
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create query
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName",Customer.class);
		
		//execute the query
		List<Customer> customers = theQuery.getResultList();
		
		//return results
		return customers;		
		
	}


	@Override
	public void saveCustomer(Customer theCustomer) {
		//get the current session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//save the customer
		currentSession.saveOrUpdate(theCustomer); // added for updating or saving the object
		
		
	}


	@Override
	public Customer getCustomer(int theId) {
		//get current session
		
		Session session = sessionFactory.getCurrentSession();
		
		//read from db with the primary key
		
		Customer customer = session.get(Customer.class,theId);
		
		return customer;
	}


	@Override
	public void deleteCustomer(int theId) {
		//get current session
		
		Session session = sessionFactory.getCurrentSession();
		
		//delete the customer
		Query theQuery = session.createQuery("delete from Customer where id=:customerId");
				theQuery.setParameter("customerId", theId);
		theQuery.executeUpdate();
	}

}
