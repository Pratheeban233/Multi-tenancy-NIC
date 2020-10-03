package com.nic.multitenant.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nic.multitenant.model.Employee;
import com.nic.multitenant.model.Login;
import com.nic.multitenant.model.RequestData;

public interface MachineTestRepo  {

	public boolean checkUserCredentials(Login data);
	
	public Object findByEmpId(long id,String token) throws JsonProcessingException;

}
