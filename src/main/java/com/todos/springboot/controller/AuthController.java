package com.todos.springboot.controller;

import com.todos.springboot.dto.JwtAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.todos.springboot.dto.LoginDto;
import com.todos.springboot.dto.RegisterDto;
import com.todos.springboot.service.AuthService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	//Build Register REST API
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
		
		String response = authService.register(registerDto);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	//Build Login REST API
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
		
		JwtAuthResponse jwtAuthResponse = authService.login(loginDto);
		
		return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
	}
	
	

}
