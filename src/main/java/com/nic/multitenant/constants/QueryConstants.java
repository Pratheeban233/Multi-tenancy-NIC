package com.nic.multitenant.constants;

public interface QueryConstants {

	public String VALIDATE_USER_CREDENTIALS = "Select 1 from Employee where email = ? and password = ?";
	
	public String FETCH_EMPLOYEE_DATA = "SELECT ID,NAME,DOB,EMAIL FROM EMPLOYEE WHERE ID = ?";
}
