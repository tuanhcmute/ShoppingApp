package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.expection.InternalServerException;
import com.example.demo.request.LoginRequest;
import com.example.demo.response.ResponseObject;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import com.example.demo.util.HttpStatusUtil;
import com.example.demo.util.PathUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = PathUtil.PREFIX_URL + "/auth")
public class AuthController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	AuthService authService;
	
	@PostMapping(value = "/login", produces = { MediaType.APPLICATION_JSON_VALUE })
	ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
		String message = "";
		try {
			// Validate email
			if(loginRequest.getEmail() == null || loginRequest.getEmail().equals("")) {
				message = "Email is required";
				LOGGER.info(message);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						ResponseObject.builder()
									.status(HttpStatusUtil.BAD_REQUEST.getDescription())
									.message(message)
									.statusCode(HttpStatusUtil.BAD_REQUEST.getValue())
									.data("")
									.build()
				);
			}
			
			// Validate password
			if(loginRequest.getPassword() == null || loginRequest.getPassword().equals("")) {
				message = "Password is required";
				LOGGER.info(message);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						ResponseObject.builder()
									.status(HttpStatusUtil.BAD_REQUEST.toString())
									.message(message)
									.statusCode(HttpStatusUtil.BAD_REQUEST.getValue())
									.data("")
									.build()
				);
			}
			
			// Check email in db
			if(loginRequest.getPassword() == null || loginRequest.getPassword().equals("")) {
				message = "Password is required";
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						ResponseObject.builder()
									.status(HttpStatusUtil.BAD_REQUEST.toString())
									.message(message)
									.statusCode(HttpStatusUtil.BAD_REQUEST.getValue())
									.data("")
									.build()
				);
			}
			
			String token = authService.login(loginRequest);
			message = "Login successfully";
			LOGGER.info(message);
			return ResponseEntity.status(HttpStatus.OK).body(
					ResponseObject.builder()
							.status(HttpStatusUtil.OK.toString())
							.statusCode(HttpStatusUtil.OK.getValue())
							.message(message)
							.data(token)
							.build()
			);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new InternalServerException(e.getMessage());
		}
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
