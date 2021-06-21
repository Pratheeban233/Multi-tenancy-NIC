package com.nic.multitenant.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nic.multitenant.jwt.JwtUtils;
import com.nic.multitenant.model.Login;
import com.nic.multitenant.model.ResponseData;
import com.nic.multitenant.repo.MachineTestRepo;

@Slf4j
@Service
public class MachineTestService {

	@Autowired
	MachineTestRepo repo;

	@Autowired
	JwtUtils jwtUtil;

	@Autowired
	ResponseData res;

	public String authenticate(Login data) {
		String generateToken = null;
		boolean isExist = repo.checkUserCredentials(data);
		if (isExist) {
			generateToken = jwtUtil.generateToken(data);
			if (res.isToken_genFlag()) {
				res.setGeneratedToken(generateToken);
			}
		} else {
			return res.getCredentialsExist();
		}
		return "Token : " + generateToken;
	}

	public Object getEmpDetails(long id, String token) throws JsonProcessingException {

		return jwtUtil.validateJwtToken(token) ? repo.findByEmpId(id, token) : res.getTokenValidationMsg();

	}

}
