package com.nic.multitenant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nic.multitenant.jwt.JwtUtils;
import com.nic.multitenant.model.Employee;
import com.nic.multitenant.model.Login;
import com.nic.multitenant.model.RequestData;
import com.nic.multitenant.model.ResponseData;
import com.nic.multitenant.repo.MachineTestRepo;


@Service
public class MachineTestService {

	@Autowired
	MachineTestRepo repo;

	@Autowired
	JwtUtils jwtutil;

	@Autowired
	ResponseData res;

	public String authenticate(Login data) {
		String generateToken = null;
		boolean isExist = repo.checkUserCredentials(data);
		System.out.println("Credentials exist : " + isExist);

		if (isExist == true) {
			generateToken = jwtutil.generateToken(data);
			if (res.isToken_genFlag()) {
				res.setGeneratedToken(generateToken);
			}
		} else {
			return res.getCredentialsExist();

		}
		return "Token::: " + generateToken;
	}

	
	public Object getEmpDetails(long id, String token) throws JsonProcessingException {

	/*	
		try {
			if(jwtutil.validateJwtToken(token)==true?repo.findByEmpId(email,token):res.getTokenValidationMsg())
			{
			repo.findByEmpId(email,token);
			//res.setParseDbfromJwttoken("JWT Token Parser failed");
			}
			else
			{
				res.getTokenValidationMsg();
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception Occuured inside findByEmpId()");
			
			
		}*/
		
		return jwtutil.validateJwtToken(token)==true?repo.findByEmpId(id,token):res.getTokenValidationMsg();

	}

}
