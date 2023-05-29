package com.example.demo.config.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);
	
	private String jwtSecret = "doduongthaituan";
	private int jwtExpiration = 86400;
	
	public String createToken(Authentication authentication) {
		String username = "admin";
		String accessToken =  Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				// Set thời gian sống
				.setExpiration(new Date(new Date().getTime() + jwtExpiration * 1000))
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
