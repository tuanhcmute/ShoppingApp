package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;

public interface UserService {
	List<UserDto> findAll();
	UserDto upsert(User user);
	UserDto findByEmail(String email);
}
