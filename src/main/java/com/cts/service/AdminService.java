package com.cts.service;

import com.cts.entity.Admin;
import com.cts.entity.Doctor;

public interface AdminService {
	public void registerUser(Admin admin);
	public Admin getAdmin(String p);
	
}
