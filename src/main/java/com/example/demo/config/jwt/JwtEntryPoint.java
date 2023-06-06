package com.example.demo.config.jwt;

import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.example.demo.util.HttpStatusCodeUtil;
import com.example.demo.util.HttpStatusUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
// Class này để thực hiện logger những request không hợp lệ, và thực hiện trả về client response không hợp hệ
public class JwtEntryPoint implements AuthenticationEntryPoint {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtEntryPoint.class);
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		LOGGER.error("Unauthorized error message {}", authException.getMessage()); 
		String messageResult = authException.getMessage();
		String statusResult = HttpStatusUtil.UNAUTHORIZED.toString();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		PrintWriter out = response.getWriter();
		out.println("{ \"status\": \"" + statusResult + "\", ");
		out.println("\"statusCode\": \"" + HttpStatusCodeUtil.UNAUTHORIZED + "\", ");
		out.println("\"message\": \"" + messageResult + "\" }");
	}

}
