package com.example.demo.config.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenFilter extends OncePerRequestFilter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenFilter.class);

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	// Khi có request thì request sẽ phải đi qua hàm này
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			// Thực hiện lấy token từ request
			String token = getToken(request);
			LOGGER.info(token);
			
			// Kiểm tra nếu token hợp lệ thì thực hiện load user từ database vào hệ thống(spring)
			if(token != null && jwtProvider.validateToken(token)) {
				String email = jwtProvider.getSubjectFromToken(token);
				UserDetails userDetails = userDetailsService.loadUserByUsername(email);
				
				/*
				 * UsernamePasswordAuthenticationToken là một lớp trong framework Spring Security của Java. 
				 * Nó được sử dụng để đại diện cho thông tin xác thực của người dùng trong quá trình xác thực trong một ứng dụng web. 
				 * */
				UsernamePasswordAuthenticationToken authenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				/*
				 * Thiết lập thông tin xác thực trong SecurityContextHolder: Sử dụng SecurityContextHolder 
				 * để thiết lập thông tin xác thực của người dùng đã được xác thực. 
				 * Điều này cho phép Spring Security biết rằng người dùng đã được xác thực và có 
				 * quyền truy cập vào các phần của ứng dụng mà yêu cầu xác thực. 
				 * */
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
			// Để cho phép request đi tiếp qua các filter khác trong chuỗi filter của Spring Security.
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	// Thực hiện get token từ request header
	private String getToken(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if(authHeader != null && authHeader.startsWith("Bearer")) {
			return authHeader.replace("Bearer", "");
		}
		return null;
	}
}
