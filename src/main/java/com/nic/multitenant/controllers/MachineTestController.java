package com.nic.multitenant.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nic.multitenant.constants.ErrorConstats;
import com.nic.multitenant.datasourceconfig.DataSourceProperties;
import com.nic.multitenant.jwt.JwtUtils;
import com.nic.multitenant.model.Employee;
import com.nic.multitenant.model.Login;
import com.nic.multitenant.model.ResponseData;
import com.nic.multitenant.model.Tenant;
import com.nic.multitenant.service.MachineTestService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MachineTestController {

	@Autowired
	MachineTestService serv;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	Tenant tenant;

	@Autowired
	DataSourceProperties properties;

	@Autowired
	ResponseData res;

	@Autowired
	JwtUtils jwtUtils;

	@ResponseBody
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcomeGreeting() {
		return "Welcome To Machine Test project";
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = { "application/json" })
	public String authenticate(@RequestBody Login req, HttpServletRequest httprequest) {

		log.info("Requested Tenant : {}", tenant.getTenant());
		log.info("Login Obj : {}", req.toString());
		log.debug("DataSource name : {}", properties.getDatasources().get(tenant.getTenant()));

		if (tenant.getTenant() == null) {
			return ErrorConstats.REQUIRED_TENANT_ID;
		}
		return serv.authenticate(req);
	}

	@RequestMapping(value = "/GetEmployee", method = RequestMethod.POST, consumes = { "application/json" })
	public Object getListOfEmployee(@RequestBody Employee req, HttpServletRequest request) throws JsonProcessingException {
		Object response = null;
		String token = request.getHeader("Authorization");
		log.info("incoming request id : {}", req.toString());
		response = serv.getEmpDetails(req.getId(), token);
		return response;

	}

}
