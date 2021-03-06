package com.nic.multitenant.constants;


public interface ErrorConstats {
	
	//authentication
	public final String INVALID_USER_CREDENTIALS = "INVALID USER CREDENTIALS / TENANT_ID";
	public final String REQUIRED_TENANT_ID = "404 : DATABASE NOT FOUND. PROVIDE THE PROPER TENANT_ID IN THE HEADER";
	
	//FetchData Error
	public final String EMPTY_RESULT_ACCESS = "404 : NO RECORD FOUND";
	public final String TOKEN_VALIDATION = "INVALID TOKEN";
	
	//TOKEN VALIDATION
	public final String JWT_TOKEN_SIGNATURE_ERROR = "INVALID TOKEN : TOKEN SIGNATURE ERROR";
	public final String JWT_TOKEN_EXPIRED_ERROR = "INVALID TOKEN : TOKEN EXPIRED";
	public final String JWT_TOKEN_MALFORMED_ERROR = "INVALID TOKEN : TOKEN FORMATION ERROR";
	public final String JWT_TOKEN_UNSUPPORTED_ERROR = "INVALID TOKEN : UNSUPPORTED TOKEN";
	public final String JWT_TOKEN_ILLEGALARGS_ERROR = "TOKEN REQUIRED / ILLEGAL ARGUMENTS IN HEADER";

}
