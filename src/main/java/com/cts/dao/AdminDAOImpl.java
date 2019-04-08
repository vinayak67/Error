package com.cts.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cts.entity.Admin;
import com.cts.entity.Customer;
import com.cts.entity.UserLogin;

@Repository("adminDAO")
public class AdminDAOImpl implements AdminDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void registerUser(Admin admin) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(admin);
		
	}

	@Override
	public Admin getAdmin(String p) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		System.out.println(p+"int he DAOImpl");
		
		
		UserLogin login=session.get(UserLogin.class,p);
		int i= login.getId();
		Admin admin=session.get(Admin.class,i);
		System.out.println(admin);
		return admin;
		
	}
	

}
