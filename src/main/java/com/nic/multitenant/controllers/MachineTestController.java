package com.nic.multitenant.controllers;

import java.io.FileNotFoundException;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nic.multitenant.constants.Constant;
import com.nic.multitenant.constants.ErrorConstats;
import com.nic.multitenant.datasourceconfig.DataSourceProperties;
import com.nic.multitenant.jwt.JwtUtils;
import com.nic.multitenant.model.Employee;
import com.nic.multitenant.model.Login;
import com.nic.multitenant.model.RequestData;
import com.nic.multitenant.model.ResponseData;
import com.nic.multitenant.model.Tenant;
import com.nic.multitenant.service.MachineTestService;
import com.nic.multitenant.tenantconfig.ThreadTenantStorage;




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
	public String welcomeGreeting() 

	{

		return "Welcome To Machine Test project";
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = { "application/json" })
	public String authenticate(@RequestBody Login req, HttpServletRequest httprequest) {
		
		System.out.println("Requested Tenant : "+tenant.getTenant());
		System.out.println("Login Obj : " + req.toString());
		
		System.out.println("datasources name : "+properties.getDatasources().get(tenant.getTenant()));

		if(tenant.getTenant() == null)
		{
			return ErrorConstats.REQUIRED_TENANT_ID;
		}
		/*
		 * if(true) { return "Please provide the valid tenant id"; }
		 */
		
			return serv.authenticate(req);
		}

	@RequestMapping(value = "/GetEmployee", method = RequestMethod.POST, consumes = { "application/json" })
	public Object getListofEmployee(@RequestBody Employee req, HttpServletRequest request) throws JsonProcessingException {

		 Object response = null;
		String token = request.getHeader("Authorization");
		System.out.println("incoming reques id :"+req.toString());

		//	ObjectMapper mapper = new ObjectMapper();
	//	Map<String, String> map = mapper.readValue(email, Map.class);
	//	System.out.println("writeValueAsString.email : "+map.get("email"));
		
	
	//	response = serv.getEmpDetails(map.get("email"),token);
		
		response = serv.getEmpDetails(req.getId(),token);
		
		System.out.println("GetEmployee() Response : "+ response);
		
		return response;

	}

}
