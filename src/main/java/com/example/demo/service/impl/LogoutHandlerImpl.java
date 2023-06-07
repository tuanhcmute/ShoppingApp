package com.example.demo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Service
public class LogoutHandlerImpl implements LogoutHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogoutHandlerImpl.class);

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		String authHeader = request.getHeader("Authorization");
		if(authHeader != null && authHeader.startsWith("Bearer")) {
			String accessToken = authHeader.replace("Bearer", "");
			LOGGER.info(accessToken);
		}
		return;
	}

}
