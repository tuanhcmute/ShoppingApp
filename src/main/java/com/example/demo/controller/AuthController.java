package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.util.PathUtil;

@RestController
@RequestMapping(path = PathUtil.PREFIX_URL + "/auth")
public class AuthController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
	
	@PostMapping("/login")
	ResponseEntity<?> login() {
		LOGGER.info("Login");
		return null;
	}
	
	@PostMapping("/register")
	ResponseEntity<?> register() {
		return null;
	}
	
	@PostMapping("/forgot-password")
	ResponseEntity<?> forgotPassword() {
		return null;
	}
}
