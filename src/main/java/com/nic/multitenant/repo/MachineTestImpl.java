package com.nic.multitenant.repo;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JWindow;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nic.multitenant.constants.ErrorConstats;
import com.nic.multitenant.constants.QueryConstants;
import com.nic.multitenant.datasourceconfig.DataSourceConfiguration;
import com.nic.multitenant.datasourceconfig.DataSourceProperties;
import com.nic.multitenant.datasourceconfig.TenantRoutingDataSource;
import com.nic.multitenant.jwt.JwtUtils;
import com.nic.multitenant.mapper.EmployeeMapper;
import com.nic.multitenant.model.Employee;
import com.nic.multitenant.model.Login;
import com.nic.multitenant.model.RequestData;
import com.nic.multitenant.model.ResponseData;
import com.nic.multitenant.model.Tenant;
import com.nic.multitenant.tenantconfig.ThreadTenantStorage;



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
			
				System.out.println("Inside emp validateCredentials()");
				System.out.println("email : " + req.getEmail() + " " + "pass : " + req.getPass());
				count = jdbcTemplate.queryForObject(QueryConstants.VALIDATE_USER_CREDENTIALS,
						new Object[] { req.getEmail(), req.getPass() }, Integer.class);

				return count > 0;
			
		} catch (Exception e) {
			
			System.out.println("Inside checkUserCredentials() catch block");
			res.setCredentialsExist(ErrorConstats.INVALID_USER_CREDENTIALS);
		}
		return count > 0;
	}

	@Override
	public Object findByEmpId(long id, String token) throws JsonProcessingException {

	//	JdbcTemplate jdbcTemplate1 = new JdbcTemplate(dbconfig.dataSource());
	//	List<Employee> list = new ArrayList<Employee>();
	
		Object response = null;
	//	String actualEmailID = email.trim();
	//	System.out.println("actualEmailID : "+actualEmailID);
	
		try {
		 String databaseNameFromJwtToken = jwtutil.getDatabaseNameFromJwtToken(token);
		 String tenantID = databaseNameFromJwtToken.split("#")[0];
		 String tokenemail = databaseNameFromJwtToken.split("#")[1];
		 String tokenpass = databaseNameFromJwtToken.split("#")[2];
		 System.out.println("JWTtoken parsed tenantID : " + tenantID);
		 System.out.println("parsed EmailID : "+tokenemail);
		 System.out.println("Incoming requestData : "+id);
		 
		 Login logindata = new Login();
		 logindata.setEmail(tokenemail);
		 logindata.setPass(tokenpass);
		 
	//	 String generateNewTokenForValidation = jwtutil.generateToken(logindata);
		 
		// if(token.equals(generateNewTokenForValidation))
		 
		 //setting the tenant id to trigger the respective datasource based on jwt token parsed
		 tenant.setTenant(tenantID.trim());
		
		 //	 trigger.determineCurrentLookupKey(tenantID.trim());
		 //	 ThreadTenantStorage.setTenantId(tenantID);
		 //	System.out.println("jdbcTemplate connection check :"+jdbcTemplate.toString());
		
	
		 response = jdbcTemplate.queryForObject(QueryConstants.FETCH_EMPLOYEE_DATA, new Object[] {id} ,new EmployeeMapper());
	
	//  emp = (Employee) jdbcTemplate.queryForObject(QueryConstants.FETCH_EMPLOYEE_DATA,, new Object[]{actualEmailID}, new EmployeeMapper());
		 
	//	 emp = (Employee) jdbcTemplate.queryForObject(QueryConstants.FETCH_EMPLOYEE_DATA, BeanPropertyRowMapper.newInstance(Employee.class),actualEmailID);
		 	 
	//	 System.out.println("emp obj : "+emp.toString()); 
	
	/*	 jdbcTemplate.query("Select * from employee",new ResultSetExtractor<List<Employee>>(){ 
			 @Override  
		     public List<Employee> extractData(ResultSet rs) throws SQLException,  
		            DataAccessException {  
		      
		        while(rs.next()){  
		        Employee e=new Employee();  
		        e.setId(rs.getInt(1));  
		        e.setName(rs.getString(2));  
		        e.setDob(rs.getString(3));
		        e.setEmail(rs.getString(4));
		        list.add(e);  
		        }  
		        
		      //  list.stream().forEach((emp) -> System.out.println(emp.getEmail() == actualEmailID ? true:false));

		        for (Employee emp : list)
		        {
		        	if(emp.getEmail().equalsIgnoreCase(actualEmailID))
		        	{
		        		System.out.println(emp.toString());
		        		//break;
		        	}
		        }
		        return list;  
		        }  
		    });  */
		}
		catch(EmptyResultDataAccessException e)
		{
			response = res.setFetchEmpData(ErrorConstats.EMPTY_RESULT_ACCESS);
			e.printStackTrace();
		}
		
		return response;
	}

}
