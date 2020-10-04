package com.nic.multitenant.datasourceconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.nic.multitenant.model.Tenant;
import com.nic.multitenant.tenantconfig.ThreadTenantStorage;

public class TenantRoutingDataSource extends AbstractRoutingDataSource {

	@Autowired
	Tenant tenant;

	@Override
	protected Object determineCurrentLookupKey() {

		String tenantId = ThreadTenantStorage.getTenantId();
		System.out.println("Requested tenantId ::: " + tenantId);

		return tenantId != null ? tenantId : tenant.getTenant();
	}

	// test
	public Object determineCurrentLookupKey(String tenantId) {

			ThreadTenantStorage.setTenantId(tenantId);
		 tenantId = ThreadTenantStorage.getTenantId();
		System.out.println("Own call() tenantId ::: " + tenantId);

		return tenantId;
	}
}
