package com.cts.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cts.entity.CheckupRequest;
import com.cts.entity.Customer;
import com.cts.entity.Doctor;
import com.cts.entity.Services;
import com.cts.entity.UserLogin;

@Repository("customerdao")
public class CustomerDAOImpl implements CustomerDAO{

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void registerUser(Customer customer) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(customer);
		//return null;
	}
	
	@Override
	public List<Services> serviceList() {
		// TODO Auto-generated method stub
		
		Session session= sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Services> query= builder.createQuery(Services.class);
		Root<Services> root = query.from(Services.class);
		query.select(root);
		Query query2= session.createQuery(query);
		return query2.getResultList();
	}

	@Override
	public List<Doctor> doctorList() {
		// TODO Auto-generated method stub
		Session session= sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Doctor> query= builder.createQuery(Doctor.class);
		Root<Doctor> root = query.from(Doctor.class);
		query.select(root);
		Query query2= session.createQuery(query);
		return query2.getResultList();
	}

	@Override
	public Customer getCustomer(String id) {
		Session session=sessionFactory.getCurrentSession();
		System.out.println(id+"int he DAOImpl");
		
		
		UserLogin login=session.get(UserLogin.class,id);
		int i= login.getId();
		Customer customer=session.get(Customer.class,i);
		System.out.println(customer);
		return customer;
	}

	@Override
	public void saveRequest(CheckupRequest request) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(request);
	}

	
	@Override
	public List<CheckupRequest> getRequest(String p) {
		// TODO Auto-generated method stub
		
		Session session =sessionFactory.getCurrentSession();
//		Criteria criteria = session.createCriteria(Doctor.class);
//		Criterion nameCriterion = Restrictions.eq("City", "pune");
//		criteria.add(nameCriterion);
//		List books = criteria.list();
//		System.out.println(books);
		 
		String sql="select *\r\n" + 
				"  from checkup_request_tab\r\n" +  
				"  join customer\r\n" + 
				"    on customer.Customer_Id\r\n" + 
				"     = checkup_request_tab.Customer_ID\r\n"; 
		
//		String sql2= 
//				" from doctor_table \r\n" + 
//				"left outer\r\n" + 
//				"  join checkup_request_tab\r\n" + 
//				"    on doctor_table.Doctor_ID\r\n" + 
//				"     = checkup_request_tab.Doctor_ID\r\n" + 
//				" where checkup_request_tab.Status = 'pending' ";
		
		SQLQuery query = session.createSQLQuery(sql);
		//query.addEntity(Doctor.class); 
		query.addEntity(CheckupRequest.class);
		List <CheckupRequest>results = query.list();
//		List cats = session.createCriteria(Doctor.class)
//			    .add( Restrictions.eq("checkup_request_tab.Status", "pending") )
//			    .list();   
//		 
//		for(int i=0;i<results.size();i++){
//	    System.out.println(results.get(i));
//	} 
		for(CheckupRequest checkupRequest : results) {
			System.out.println(checkupRequest.getDate());
			System.out.println(checkupRequest.getDid());
			System.out.println(checkupRequest.getStatus());
		}
		System.out.println(results.toString());
		return results;
	}

	@Override
	public List<CheckupRequest> getReports(int id) {
		
		Session session=sessionFactory.getCurrentSession();
		
		CriteriaBuilder cb=session.getCriteriaBuilder();
		
		CriteriaQuery<Customer> query = cb.createQuery(Customer.class);
		Root<Customer> cutomer = query.from(Customer.class);
		Join<CheckupRequest,Customer> request=cutomer.join("requests");
		query.select(request).where(cb.equal(request.get("status"), "Finished"));
		
		Query pop =session.createQuery(query);
		List<CheckupRequest> results = pop.getResultList();

		return results;
	}

	@Override
	public List<Customer> allCustomers() {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		CriteriaBuilder cb=session.getCriteriaBuilder();
		
		CriteriaQuery<Customer> query= cb.createQuery(Customer.class);
		Root<Customer> root = query.from(Customer.class);
		query.select(root);
		Query query2= session.createQuery(query);
		return query2.getResultList();
	}

	@Override
	public Customer getCustomerById(int id) {
		 Session session=sessionFactory.getCurrentSession();
		 Customer customer=session.get(Customer.class,id);
			System.out.println(customer);
			return customer;
				
	}	
	
	
}
