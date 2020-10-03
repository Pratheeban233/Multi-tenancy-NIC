package com.nic.multitenant.datasourceconfig;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfiguration {
	
	private final DataSourceProperties dataSourceProperties;

	  public DataSourceConfiguration(DataSourceProperties dataSourceProperties) {
	    this.dataSourceProperties = dataSourceProperties;
	  }

	  @Bean
	  public DataSource dataSource() {
	    TenantRoutingDataSource customDataSource = new TenantRoutingDataSource();
	    customDataSource.setTargetDataSources(
	        dataSourceProperties.getDatasources());
	    return customDataSource;
	  }

}
