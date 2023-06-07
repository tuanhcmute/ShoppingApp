package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.UserRequest;
import com.example.demo.service.UserService;
import com.example.demo.util.IdUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

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
	public UserDto create(UserRequest userRequest) {
		Set<Role> roles = new HashSet<>();
		
		userRequest.getRoles().forEach(roleId -> {
			Optional<Role> role = roleRepository.findById(roleId);
			if(role.isPresent()) {
				if(!roles.contains(role.get())) {
					roles.add(role.get());
				}
			}
		});
		
		User user = User.builder()
						.id(IdUtil.generatedId())
						.email(userRequest.getEmail())
						.fullName(userRequest.getFullName())
						.hashedPassword(passwordEncoder.encode(userRequest.getPassword()))
						.roles(roles)
						.build();
		
		User u = userRepository.save(user);
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

	@Override
	public boolean existsByEmail(String email) {
		User user = userRepository.findByEmail(email);
		return user != null;
	}
	
}
