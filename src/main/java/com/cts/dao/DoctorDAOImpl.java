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
import com.cts.entity.Doctor;
import com.cts.entity.UserLogin;

@Repository("doctorDAO")
public class DoctorDAOImpl implements DoctorDAO{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveDoctor(Doctor doctor) {
		
		sessionFactory.getCurrentSession().saveOrUpdate(doctor);
		
	}


	@Override
	public Doctor getDoctor(String id) {
		Session session=sessionFactory.getCurrentSession();
		
	UserLogin login=session.get(UserLogin.class,id);
	int i= login.getId();
	Doctor theDoctor =session.get(Doctor.class,i);
	System.out.println(theDoctor);
		return theDoctor;
		
	}


	@Override
	public List<CheckupRequest> getRequest(String p) {
		Session session=sessionFactory.getCurrentSession();
		
		UserLogin login=session.get(UserLogin.class,p);
		int id= login.getId();
		CriteriaBuilder cb=session.getCriteriaBuilder();
		
		CriteriaQuery<Doctor> query = cb.createQuery(Doctor.class);
		Root<Doctor> doctor = query.from(Doctor.class);
		Join<CheckupRequest,Doctor> request=doctor.join("requests");
		query.select(request).where(cb.equal(request.get("status"), "Pending")).where(cb.equal(request.get("did"), id));
		
		Query pop =session.createQuery(query);
		List<CheckupRequest> results = pop.getResultList();

		return results;
	}

	
	@Override
	public List<CheckupRequest> getAcceptedRequests(int id) {
		
		Session session=sessionFactory.getCurrentSession();
		
		CriteriaBuilder cb=session.getCriteriaBuilder();
		
		CriteriaQuery<Doctor> query = cb.createQuery(Doctor.class);
		Root<Doctor> doctor = query.from(Doctor.class);
		Join<CheckupRequest,Doctor> request=doctor.join("requests");
		query.select(request).where(cb.equal(request.get("status"), "Accepted")).where(cb.equal(request.get("did"), id));
		
		Query pop =session.createQuery(query);
		List<CheckupRequest> results = pop.getResultList();
		return results;
		
	}


	@Override
	public List<CheckupRequest> getReports(int id) {
		
		Session session=sessionFactory.getCurrentSession();
		
		CriteriaBuilder cb=session.getCriteriaBuilder();
		
		CriteriaQuery<Doctor> query = cb.createQuery(Doctor.class);
		Root<Doctor> doctor = query.from(Doctor.class);
		Join<CheckupRequest,Doctor> request=doctor.join("requests");
		query.select(request).where(cb.equal(request.get("status"), "Finished")).where(cb.equal(request.get("did"), id));
		
		Query pop =session.createQuery(query);
		List<CheckupRequest> results = pop.getResultList();

		return results;
	}	
	

	
	

	@Override
	public List<Doctor> doctorList() {
		
		Session session=sessionFactory.getCurrentSession();
		CriteriaBuilder cb=session.getCriteriaBuilder();
		CriteriaQuery<Doctor> cq=cb.createQuery(Doctor.class);
		Root<Doctor> root =cq.from(Doctor.class);
		cq.select(root);
		Query query =session.createQuery(cq);
		System.out.println("YOLOOOO"+query.getResultList());
		return query.getResultList();
	}


	@Override
	public void deleteDoctor(int id) {

		Session session=sessionFactory.getCurrentSession();
		System.out.println("In DAOOOO");
		Doctor doctor =session.byId(Doctor.class).load(id);
		session.delete(doctor);
		
	}	
	
}
