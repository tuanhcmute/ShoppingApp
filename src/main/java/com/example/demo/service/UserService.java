package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.UserDto;
import com.example.demo.request.UserRequest;

public interface UserService {
	List<UserDto> findAll();
	UserDto create(UserRequest userRequest);
	UserDto findByEmail(String email);
	boolean existsByEmail(String email);
}
