package com.cts.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="medicare_services_table")
public class Services {
	
	public Services() {
		
	}
	
	@Id
	@Column(name="medicare_service_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int medicare_service_id;
	
	@Column(name="medicare_service",length=50,unique=true)
	String medicare_service;	
	
	
	
	@Column(name="service_description",length=100)
	String service_description;
	
	@Column(name="amount",length=10)
	int amount;

	public int getMedicare_service_id() {
		return medicare_service_id;
	}

	public void setMedicare_service_id(int medicare_service_id) {
		this.medicare_service_id = medicare_service_id;
	}

	public String getMedicare_service() {
		return medicare_service;
	}

	public void setMedicare_service(String medicare_service) {
		this.medicare_service = medicare_service;
	}



	public String getService_description() {
		return service_description;
	}

	public void setService_description(String service_description) {
		this.service_description = service_description;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Services [medicare_service_id=" + medicare_service_id + ", medicare_service=" + medicare_service
				+ ", service_description=" + service_description + ", amount=" + amount + "]";
	}

	
	
	

	
	
	
}
