package com.example.demo.config.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.demo.model.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
// Class này thực hiện các công việc tạo token, validate token, get email(username từ token)
public class JwtProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);
	
	private final String jwtSecret = "1ac8dde22c6b454da0923af29afd67bf";
	private final int jwtExpiration = 86400; // 1 day
	
	private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().after(new Date());
	}

	
	public String createToken(Authentication authentication) {
		// Thực hiện lấy user từ hệ thống
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		
		// Build accessToken
		String accessToken =  Jwts.builder()
				.setSubject(customUserDetails.getEmail())
				.setIssuedAt(new Date())
				// Set thời gian sống
				.setExpiration(generateExpirationDate()) // 1 day
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
		LOGGER.info(accessToken);
		return accessToken;
	}
	
	public Date generateExpirationDate() {
		return new Date(new Date().getTime() + jwtExpiration * 1000);
	}
	
	public boolean validateToken(String token) {
		try {
			// Thực hiện convert token 
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		}  catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty: {}", e.getMessage());
        }
		return false;
	}
	

	public String getSubjectFromToken(String token) {
		 Claims claims = Jwts.parser()
					.setSigningKey(jwtSecret)
					.parseClaimsJws(token)
					.getBody();
		 if(claims != null && isTokenExpired(claims) ) {
			String username = claims.getSubject();
			return username;
		 }
		return null; 
	}
}
