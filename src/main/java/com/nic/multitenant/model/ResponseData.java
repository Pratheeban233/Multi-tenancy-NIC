package com.nic.multitenant.model;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Component
public class ResponseData {

	private String credentialsExist = null;
	private boolean token_genFlag = false;
	private String generatedToken = null;
	private Object fetchEmpData = null;
	private String parseDbfromJwttoken = null;
	private String tokenValidationMsg = null;

	public String getCredentialsExist() {
		return credentialsExist;
	}

	public void setCredentialsExist(String credentialsExist) {
		this.credentialsExist = credentialsExist;
	}

	public boolean isToken_genFlag() {
		return token_genFlag;
	}

	public void setToken_genFlag(boolean token_genFlag) {
		this.token_genFlag = token_genFlag;
	}

	public String getGeneratedToken() {
		return generatedToken;
	}

	public void setGeneratedToken(String generatedToken) {
		this.generatedToken = generatedToken;
	}

	public Object getFetchEmpData() {
		return fetchEmpData;
	}

	public Object setFetchEmpData(Object fetchEmpData) {
		return this.fetchEmpData = fetchEmpData;
	}

	public String getParseDbfromJwttoken() {
		return parseDbfromJwttoken;
	}

	public void setParseDbfromJwttoken(String parseDbfromJwttoken) {
		this.parseDbfromJwttoken = parseDbfromJwttoken;
	}

	public String getTokenValidationMsg() {
		return tokenValidationMsg;
	}

	public void setTokenValidationMsg(String tokenValidationMsg) {
		this.tokenValidationMsg = tokenValidationMsg;
	}

}
