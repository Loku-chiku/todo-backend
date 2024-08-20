package com.todos.springboot.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.todos.springboot.dto.JwtAuthResponse;
import com.todos.springboot.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todos.springboot.dto.LoginDto;
import com.todos.springboot.dto.RegisterDto;
import com.todos.springboot.entity.Role;
import com.todos.springboot.entity.User;
import com.todos.springboot.exception.TodoAPIException;
import com.todos.springboot.repository.RoleRepository;
import com.todos.springboot.repository.UserRepository;
import com.todos.springboot.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public String register(RegisterDto registerDto) {

		// check username is already exists in database
		if(userRepository.existsByUsername(registerDto.getUsername())) {
			throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Username already exists!.");
		}
		
		// check email is already exists in database
		if(userRepository.existsByEmail(registerDto.getEmail())) {
			throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
		}
		
		User user = new User();
		user.setName(registerDto.getName());
		user.setUsername(registerDto.getUsername());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName("ROLE_USER");
		roles.add(userRole);
		
		user.setRoles(roles);
		
		userRepository.save(user);
		
		return "User Registered Successfully!.";
	}

	@Override
	public JwtAuthResponse login(LoginDto loginDto) {

		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginDto.getUsernameOrEmail(), 
				loginDto.getPassword()
		));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication);

		Optional<User> userOptional = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(),
				loginDto.getUsernameOrEmail());

		String role = null;

		if(userOptional.isPresent()){
			User loggedInUser = userOptional.get();
			Optional <Role> optionalRole = loggedInUser.getRoles().stream().findFirst();

			if(optionalRole.isPresent()){
				Role userRole = optionalRole.get();
				role = userRole.getName();
			}
		}

		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setRole(role);
		jwtAuthResponse.setAccessToken(token);
		
		return jwtAuthResponse;
	}

}
