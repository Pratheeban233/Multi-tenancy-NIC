package com.nic.multitenant.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

	@RequestMapping("/root")
	public String welcome() {
		return "Welcome to multitenant project";
	}
}
