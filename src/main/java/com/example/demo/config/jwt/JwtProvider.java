package com.example.demo.config.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.demo.model.CustomUserDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
// Class này thực hiện các công việc tạo token, validate token, get email(username từ token)
public class JwtProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);
	
	private final String jwtSecret = "1ac8dde22c6b454da0923af29afd67bf";
	private final int jwtExpiration = 86400; // 1 day
	
	public String createToken(Authentication authentication) {
		// Thực hiện lấy user từ hệ thống
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		
		// Build accessToken
		String accessToken =  Jwts.builder()
				.setSubject(customUserDetails.getEmail())
				.setIssuedAt(new Date())
				// Set thời gian sống
				.setExpiration(new Date(new Date().getTime() + jwtExpiration * 1000)) // 1 day
				.signWith(SignatureAlgorithm.HS256, jwtSecret)
				.compact();
		LOGGER.info(accessToken);
		return accessToken;
	}
	
	public boolean validateToken(String token) {
		try {
			// Thực hiện convert token 
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return false;
	}
	
	public String getSubjectFromToken(String token) {
		String username = Jwts.parser()
							.setSigningKey(jwtSecret)
							.parseClaimsJws(token)
							.getBody().getSubject();
		return username;
	}
}
