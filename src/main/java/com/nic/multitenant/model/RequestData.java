package com.nic.multitenant.model;

public class RequestData {

	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "RequestData [id=" + id + "]";
	}
	
	
}
