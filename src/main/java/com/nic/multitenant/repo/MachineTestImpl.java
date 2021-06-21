package com.nic.multitenant.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nic.multitenant.constants.ErrorConstats;
import com.nic.multitenant.constants.QueryConstants;
import com.nic.multitenant.datasourceconfig.DataSourceConfiguration;
import com.nic.multitenant.datasourceconfig.TenantRoutingDataSource;
import com.nic.multitenant.jwt.JwtUtils;
import com.nic.multitenant.mapper.EmployeeMapper;
import com.nic.multitenant.model.Login;
import com.nic.multitenant.model.ResponseData;
import com.nic.multitenant.model.Tenant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("ConstantConditions")
@Component
public class MachineTestImpl implements MachineTestRepo {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	ResponseData res;

	@Autowired
	Tenant tenant;

	@Autowired
	JwtUtils jwtutil;

	@Autowired
	TenantRoutingDataSource trigger;

	@Autowired
	DataSourceConfiguration dbconfig;

	@Override
	public boolean checkUserCredentials(Login req) {
		int count = 0;
		try {
			log.info("email : {} and pass : {}", req.getEmail(), req.getPass());
			count = jdbcTemplate.queryForObject(QueryConstants.VALIDATE_USER_CREDENTIALS, new Object[] { req.getEmail(), req.getPass() }, Integer.class);
			return count > 0;
		} catch (Exception e) {
			res.setCredentialsExist(ErrorConstats.INVALID_USER_CREDENTIALS);
		}
		return count > 0;
	}

	@Override
	public Object findByEmpId(long id, String token) throws JsonProcessingException {
		Object response = null;
		try {
			String databaseNameFromJwtToken = jwtutil.getDatabaseNameFromJwtToken(token);
			String tenantID = databaseNameFromJwtToken.split("#")[0];
			String tokenemail = databaseNameFromJwtToken.split("#")[1];
			String tokenpass = databaseNameFromJwtToken.split("#")[2];
			log.debug("JWTtoken parsed tenantID : {}", tenantID);
			log.debug("parsed EmailID : {}", tokenemail);
			log.debug("Incoming requestData : {}", id);

			Login logindata = new Login();
			logindata.setEmail(tokenemail);
			logindata.setPass(tokenpass);

			// setting the tenant id to trigger the respective datasource based on jwt token parsed
			tenant.setTenant(tenantID.trim());
			response = jdbcTemplate.queryForObject(QueryConstants.FETCH_EMPLOYEE_DATA, new Object[] { id }, new EmployeeMapper());
		} catch (EmptyResultDataAccessException e) {
			res.setFetchEmpData(ErrorConstats.EMPTY_RESULT_ACCESS);
			e.printStackTrace();
		}

		return response;
	}

}
