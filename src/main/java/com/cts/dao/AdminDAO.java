package com.cts.dao;

import com.cts.entity.Admin;

public interface AdminDAO {
	public void registerUser(Admin admin);
	public Admin getAdmin(String p);
	
}
