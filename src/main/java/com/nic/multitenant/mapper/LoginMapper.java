package com.nic.multitenant.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nic.multitenant.model.Login;


public class LoginMapper implements RowMapper<Login> {

	@Override
	public Login mapRow(ResultSet rs, int rowNum) throws SQLException {

		Login login =  new Login();
		login.setEmail(rs.getString("email"));
		login.setPass(rs.getString("pass"));
		
		return login;
	}

}
