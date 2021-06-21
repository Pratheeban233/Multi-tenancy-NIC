package com.nic.multitenant.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ResponseData {

	private String credentialsExist = null;
	private boolean token_genFlag = false;
	private String generatedToken = null;
	private Object fetchEmpData = null;
	private String parseDbfromJwttoken = null;
	private String tokenValidationMsg = null;

}
