package com.nic.multitenant.model;

import org.springframework.stereotype.Component;

@Component
public class Login {

	private String email;
	private String pass;

	public Login() {

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Override
	public String toString() {
		return "Login [email=" + email + ", pass=" + pass + "]";
	}

}
