package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.config.jwt.JwtProvider;
import com.example.demo.request.LoginRequest;
import com.example.demo.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtProvider jwtProvider;
	
	@Override
	public String login(LoginRequest loginRequest) {
		// Thực hiện xác thực người dùng
		Authentication authentication =  authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
		);
		// Điều này cho phép truy cập thông tin về người dùng đã xác thực trong toàn bộ quá trình xử lý yêu cầu
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// Thực hiện tạo token từ authentication (Cái authen này tượng trung cho người dùng đã login)
		String accessToken = jwtProvider.createToken(authentication);
		return accessToken;
	}

}
