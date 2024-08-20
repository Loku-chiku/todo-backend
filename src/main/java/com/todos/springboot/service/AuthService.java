package com.todos.springboot.service;

import com.todos.springboot.dto.JwtAuthResponse;
import com.todos.springboot.dto.LoginDto;
import com.todos.springboot.dto.RegisterDto;

public interface AuthService {

	String register(RegisterDto registerDto);
	
	JwtAuthResponse login(LoginDto loginDto);
}
