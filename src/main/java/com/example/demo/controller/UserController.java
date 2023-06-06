package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseObject;
import com.example.demo.dto.UserDto;
import com.example.demo.expection.InternalServerException;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.util.HttpStatusCodeUtil;
import com.example.demo.util.HttpStatusUtil;
import com.example.demo.util.PathUtil;

@RestController
@RequestMapping(path = PathUtil.PREFIX_URL + "/users")
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired 
	UserService userService;
	
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
				.statusCode(HttpStatusCodeUtil.OK)
				.data(userDtos)
				.build()
			);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new InternalServerException(e.getMessage());

		}
	}
	// Upsert
	@PostMapping("/upsert")
	ResponseEntity<ResponseObject> upsert(@RequestBody User user) {
		try {
			return null;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new InternalServerException(e.getMessage());
		}
	}
}
