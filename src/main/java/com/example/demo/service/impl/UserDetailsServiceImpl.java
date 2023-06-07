package com.example.demo.service.impl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.CustomUserDetails;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	// Thực hiện Build ra một userdetails của hệ thống
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User user = userRepository.findByEmail(username);
			return CustomUserDetails.builder()
					.id(user.getId())
					.email(user.getEmail())
					.fullName(user.getFullName())
					.hashedPassword(user.getHashedPassword())
					.roles(user.getRoles().stream()
							.map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()))
					.build();
		} catch (Exception e) {
			throw new Error(e.getMessage());
		}
	}

}
