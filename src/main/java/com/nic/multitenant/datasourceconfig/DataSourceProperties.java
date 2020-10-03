package com.nic.multitenant.datasourceconfig;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.nic.multitenant.model.Tenant;

@Component
@Configuration
@ConfigurationProperties(prefix = "tenants")
public class DataSourceProperties {
	
	@Autowired
	Tenant tenant;
	
	private Map<Object, Object> datasources = new LinkedHashMap<>();

	  public Map<Object, Object> getDatasources() {
	    return datasources;
	  }

	 
	public void setDatasources(Map<String, Map<String, String>> datasources) {
	    datasources
	        .forEach((key, value) -> this.datasources.put(key, convert(value)));
	    
	    System.out.println("datasources : "+datasources.toString());
	    
	  }

	  public DataSource convert(Map<String, String> source) {
		  
		  	System.out.println("------------------------------");
	    	System.out.println("jdbcURL : "+source.get("jdbcUrl"));
	    	System.out.println("Driver Class : "+source.get("driverClassName"));
	    	System.out.println("UserName : "+source.get("username"));
	    	System.out.println("Password : "+source.get("password"));
	    	System.out.println("------------------------------");
	    	
	    	
	    return DataSourceBuilder.create()
	        .url(source.get("jdbcUrl"))
	        .driverClassName(source.get("driverClassName"))
	        .username(source.get("username"))
	        .password(source.get("password"))
	        .build();
	  }

}
