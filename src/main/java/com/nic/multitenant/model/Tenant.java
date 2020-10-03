package com.nic.multitenant.model;

import org.springframework.stereotype.Component;

@Component
public class Tenant {
	
	private String tenant;

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	@Override
	public String toString() {
		return "Tenent [tenant=" + tenant + "]";
	}
	
	

}
