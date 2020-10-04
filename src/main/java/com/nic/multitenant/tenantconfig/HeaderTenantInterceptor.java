package com.nic.multitenant.tenantconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import com.nic.multitenant.constants.Constant;
import com.nic.multitenant.model.Tenant;

@Component
public class HeaderTenantInterceptor implements WebRequestInterceptor {

	@Autowired
	Tenant tenant;
	
//	  public static final String TENANT_HEADER = "X-tenant";

	  @Override
	  public void preHandle(WebRequest request) throws Exception {
		  
		  tenant.setTenant(request.getHeader(Constant.TENANT_HEADER));
		  
	    ThreadTenantStorage.setTenantId(request.getHeader(Constant.TENANT_HEADER));
	  }

	@Override
	public void postHandle(WebRequest request, ModelMap model) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(WebRequest request, Exception ex) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
