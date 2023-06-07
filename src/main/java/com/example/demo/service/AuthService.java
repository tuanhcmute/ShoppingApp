package com.example.demo.service;

import com.example.demo.request.LoginRequest;

public interface AuthService {
	String login(LoginRequest loginRequest);
}