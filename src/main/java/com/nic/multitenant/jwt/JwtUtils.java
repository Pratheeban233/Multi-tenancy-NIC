package com.nic.multitenant.jwt;

import java.util.Date;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nic.multitenant.constants.Constant;
import com.nic.multitenant.constants.ErrorConstats;
import com.nic.multitenant.model.Login;
import com.nic.multitenant.model.ResponseData;
import com.nic.multitenant.model.Tenant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	@Autowired
	ResponseData res;
	
	@Autowired
	Tenant tenant;
	
	private static final long serialVersionUID = -2550185165626007488L;
	
	public static final long JWT_TOKEN_VALIDITY = 5*60*60;
	
	public String generateToken(Login data) {
        Claims claims = Jwts.claims().setSubject(tenant.getTenant());
        claims.put("LoginToken", data);
        String token = Jwts.builder()
        		.setClaims(claims)
        		.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
				.signWith(SignatureAlgorithm.HS512, Constant.TOKEN_KEY)
                .compact();
        if(token != null && !token.isEmpty())
        {
        	res.setToken_genFlag(true);
        }
       System.out.println("TOKEN ::::::::::" + token);
        return token;
    }
	
	public String getDatabaseNameFromJwtToken(String token) throws JsonProcessingException {
		
		System.out.println("Token : "+token);
		String tenantId = Jwts.parser().setSigningKey(Constant.TOKEN_KEY).parseClaimsJws(token).getBody().getSubject();
		System.out.println("tenantId : "+tenantId);
		Object object = Jwts.parser().setSigningKey(Constant.TOKEN_KEY).parseClaimsJws(token).getBody().get("LoginToken");
		
		ObjectMapper mapper = new ObjectMapper();
		String userCredntial = mapper.writeValueAsString(object);
		Login data = mapper.readValue(userCredntial, Login.class);
		
		System.out.println(userCredntial+" >> "+data.getEmail()+" >> "+data.getPass());
		
		return tenantId+ "#"+data.getEmail()+ "#"+data.getPass() ;
	}
    
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(Constant.TOKEN_KEY).parseClaimsJws(authToken);
			System.out.println("Validate Token : "+Jwts.parser().setSigningKey(Constant.TOKEN_KEY).parseClaimsJws(authToken));
			return true;
		} catch (SignatureException e) {
			res.setTokenValidationMsg(ErrorConstats.JWT_TOKEN_SIGNATURE_ERROR);
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			res.setTokenValidationMsg(ErrorConstats.JWT_TOKEN_MALFORMED_ERROR);
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			res.setTokenValidationMsg(ErrorConstats.JWT_TOKEN_EXPIRED_ERROR);
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			res.setTokenValidationMsg(ErrorConstats.JWT_TOKEN_UNSUPPORTED_ERROR);
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			res.setTokenValidationMsg(ErrorConstats.JWT_TOKEN_ILLEGALARGS_ERROR);
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}

	}
