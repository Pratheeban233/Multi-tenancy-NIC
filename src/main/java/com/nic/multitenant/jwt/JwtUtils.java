package com.nic.multitenant.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nic.multitenant.constants.Constant;
import com.nic.multitenant.constants.ErrorConstats;
import com.nic.multitenant.model.Login;
import com.nic.multitenant.model.ResponseData;
import com.nic.multitenant.model.Tenant;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtils {

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	@Autowired
	ResponseData res;
	@Autowired
	Tenant tenant;

	public String generateToken(Login data) {
		Claims claims = Jwts.claims().setSubject(tenant.getTenant());
		claims.put("LoginToken", data);
		String token = Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)).signWith(SignatureAlgorithm.HS512, Constant.TOKEN_KEY)
				.compact();
		if (token != null && !token.isEmpty()) {
			res.setToken_genFlag(true);
		}
		return token;
	}

	public String getDatabaseNameFromJwtToken(String token) throws JsonProcessingException {

		log.info("Token : {}", token);
		String tenantId = Jwts.parser().setSigningKey(Constant.TOKEN_KEY).parseClaimsJws(token).getBody().getSubject();
		Object object = Jwts.parser().setSigningKey(Constant.TOKEN_KEY).parseClaimsJws(token).getBody().get("LoginToken");

		String userCredentials = new ObjectMapper().writeValueAsString(object);
		Login data = new ObjectMapper().readValue(userCredentials, Login.class);

		return tenantId + "#" + data.getEmail() + "#" + data.getPass();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(Constant.TOKEN_KEY).parseClaimsJws(authToken);
			log.debug("Validate Token : {}", Jwts.parser().setSigningKey(Constant.TOKEN_KEY).parseClaimsJws(authToken));
			return true;
		} catch (SignatureException e) {
			res.setTokenValidationMsg(ErrorConstats.JWT_TOKEN_SIGNATURE_ERROR);
			log.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			res.setTokenValidationMsg(ErrorConstats.JWT_TOKEN_MALFORMED_ERROR);
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			res.setTokenValidationMsg(ErrorConstats.JWT_TOKEN_EXPIRED_ERROR);
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			res.setTokenValidationMsg(ErrorConstats.JWT_TOKEN_UNSUPPORTED_ERROR);
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			res.setTokenValidationMsg(ErrorConstats.JWT_TOKEN_ILLEGALARGS_ERROR);
			log.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}

}
