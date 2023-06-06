package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.util.IdUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public List<UserDto> findAll() {
		List<User> users = userRepository.findAll();
		if(users == null) return null;
		List<UserDto> userDtos = new ArrayList<>();
		for (User user : users) {
			userDtos.add(UserMapper.toDto(user));
		}
		return userDtos;
	}

	@Override
	public UserDto upsert(User user) {
		User u = userRepository.findById(user.getId())
				.map(c -> {
					c.setFullName(user.getFullName());
					return userRepository.save(c);
				}).orElseGet(() -> {
					User c = User.builder()
										.id(IdUtil.generatedId())
										.email(user.getEmail())
										.fullName(user.getFullName())
										.hashedPassword(user.getHashedPassword())
										.build();
					return userRepository.save(c);
				});
				return UserMapper.toDto(u);
	}

	@Override
	public UserDto findByEmail(String email) {
		User user = userRepository.findByEmail(email);
		if(user != null) {
			return UserMapper.toDto(user);
		}
		return null;
	}
	
}
