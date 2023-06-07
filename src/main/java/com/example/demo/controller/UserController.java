package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDto;
import com.example.demo.expection.InternalServerException;
import com.example.demo.request.UserRequest;
import com.example.demo.response.ResponseObject;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.util.HttpStatusUtil;
import com.example.demo.util.PathUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = PathUtil.PREFIX_URL + "/users")
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired 
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	// Get All user
	@GetMapping("")
	ResponseEntity<ResponseObject> findAll() {
		try {
			String message = "Get all user successfully";
			// Lấy tất cả user
			List<UserDto> userDtos = userService.findAll();
			LOGGER.info(message);
			return ResponseEntity.status(HttpStatus.OK).body(
				ResponseObject.builder()
				.status(HttpStatusUtil.OK.toString())
				.message(message)
				.statusCode(HttpStatusUtil.OK.getValue())
				.data(userDtos)
				.build()
			);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new InternalServerException(e.getMessage());

		}
	}
	// Upsert
	@PostMapping(value = "/create", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<ResponseObject> upsert(@Valid @RequestBody UserRequest userRequest) {
		String message = "";
		try {
			if(userRequest.getEmail() == null || userRequest.getEmail().equals("")) {
				message = "Email is required";
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						ResponseObject.builder()
									.status(HttpStatusUtil.BAD_REQUEST.toString())
									.message(message)
									.statusCode(HttpStatusUtil.BAD_REQUEST.getValue())
									.data("")
									.build()
				);
			}
			
			if(userRequest.getPassword() == null || userRequest.getPassword().equals("")) {
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
			
			if(userRequest.getRoles() == null || userRequest.getRoles().size() == 0) {
				message = "Roles is required";
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						ResponseObject.builder()
									.status(HttpStatusUtil.BAD_REQUEST.toString())
									.message(message)
									.statusCode(HttpStatusUtil.BAD_REQUEST.getValue())
									.data("")
									.build()
				);
			}
			
			// Check user tồn tại trong hệ thống hay không
			boolean isUserExists = userService.existsByEmail(userRequest.getEmail());
			if(isUserExists) {
				message = "Email is exsits";
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						ResponseObject.builder()
									.status(HttpStatusUtil.BAD_REQUEST.toString())
									.message(message)
									.statusCode(HttpStatusUtil.BAD_REQUEST.getValue())
									.data("")
									.build()
				);
			}

			UserDto userDto = userService.create(userRequest);
			message = "Create user successfully";
			return ResponseEntity.status(HttpStatus.OK).body(
					ResponseObject.builder()
								.status(HttpStatusUtil.OK.toString())
								.message(message)
								.statusCode(HttpStatusUtil.OK.getValue())
								.data(userDto)
								.build()
			);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new InternalServerException(e.getMessage());
		}
	}
}
