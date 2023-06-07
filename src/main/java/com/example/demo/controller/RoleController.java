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

import com.example.demo.dto.RoleDto;
import com.example.demo.expection.InternalServerException;
import com.example.demo.response.ResponseObject;
import com.example.demo.service.RoleService;
import com.example.demo.util.HttpStatusUtil;
import com.example.demo.util.PathUtil;


@RestController
@RequestMapping(PathUtil.PREFIX_URL + "/roles")
public class RoleController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	RoleService roleService;
	
	@GetMapping("")
	ResponseEntity<ResponseObject> findAll() {
		try {
			List<RoleDto> roleDtos = roleService.findAll();
			String message = "Get All roles successfully";
			LOGGER.info(message);
			return roleDtos != null 
					?
					ResponseEntity.status(HttpStatus.OK).body(
								ResponseObject.builder()
									.status(HttpStatusUtil.OK.toString())
									.statusCode(HttpStatusUtil.OK.getValue())
									.message(message)
									.data(roleDtos)
									.build())
					: 
					ResponseEntity.status(HttpStatus.NO_CONTENT).body(
								ResponseObject.builder()
									.status(HttpStatusUtil.NO_CONTENT.toString())
									.statusCode(HttpStatusUtil.NO_CONTENT.getValue())
									.message(message)
									.data("")
									.build());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new InternalServerException(e.getMessage());
		}
	}
	
	@PostMapping(value = "/upsert", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<ResponseObject> upsertRole(@RequestBody RoleDto role) {
		String message = "";
		try {
			if(role == null) {
				message = "Role cannot null";
				LOGGER.warn(message);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					ResponseObject.builder()
					.status(HttpStatusUtil.BAD_REQUEST.toString())
					.message(message)
					.statusCode(HttpStatusUtil.BAD_REQUEST.getValue())
					.data("")
					.build()
				);
			}
			if(role.getId() == null || role.getId().equals("")) {
				message = "Role id is required";
				LOGGER.warn(message);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						ResponseObject.builder()
						.status(HttpStatusUtil.BAD_REQUEST.toString())
						.message(message)
						.statusCode(HttpStatusUtil.BAD_REQUEST.getValue())
						.data("")
						.build()
					);
			}
			if(role.getName() == null || role.getName().equals("")) {
				message = "Role name is required";
				LOGGER.warn(message);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						ResponseObject.builder()
						.status(HttpStatusUtil.BAD_REQUEST.toString())
						.message(message)
						.statusCode(HttpStatusUtil.BAD_REQUEST.getValue())
						.data("")
						.build()
					);
			}
			// Nếu trùng thì update, không trùng thì tiến hành create
			RoleDto roleDto = roleService.upsert(role);
			message = "Create role successfully";
			LOGGER.info(message);
			return ResponseEntity.status(HttpStatus.OK).body(
					ResponseObject.builder()
					.status(HttpStatusUtil.OK.toString())
					.message(message)
					.statusCode(HttpStatusUtil.OK.getValue())
					.data(roleDto)
					.build()
			);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new InternalServerException(e.getMessage());
		}
	}
}
