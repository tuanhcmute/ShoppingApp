package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.config.jwt.JwtEntryPoint;
import com.example.demo.config.jwt.JwtTokenFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtEntryPoint jwtEntryPoint;
	
	@Bean
	JwtTokenFilter jwtTokenFilter() {
		return new JwtTokenFilter();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager( AuthenticationConfiguration authConfig) throws Exception {
		return  authConfig.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// Thực hiện add add cái filter JWTTokenConfig này vào => Nhằm mục đích lấy token ra trước
		http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		http.cors() // disable cors
			.and()
			.csrf() // disable csrf
			.disable()
			// Create session
			// SessionCreationPolicy.STATELESS, nghĩa là Spring Security sẽ không tạo phiên (session) 
			// cho người dùng sau khi xác thực thành công.
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			// Thực hiện cho người dùng sử dụng những entrypoint bên dưới
			.authorizeHttpRequests()
			.requestMatchers("/api/v1/auth/**").permitAll()
			.requestMatchers("/api/v1/roles/**").permitAll()
			// Những enpoint khác cần phải xác thực thì mới có thể thao tác được
			.anyRequest().authenticated()
			.and()
			// Xử lý exception nếu không có quyền truy cập
			.exceptionHandling()
			.authenticationEntryPoint(jwtEntryPoint);
		
		return http.build();
	}
}
