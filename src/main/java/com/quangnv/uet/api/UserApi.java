package com.quangnv.uet.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quangnv.uet.dto.UserDto;
import com.quangnv.uet.service.UserService;
import com.quangnv.uet.token.JwtTokenProvider;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin(value = "http://localhost:4200/")
public class UserApi {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@GetMapping(value = "/auth")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<UserDto> getUserInfo() {
		UserDto userDto = userService
				.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
	}

//	@PostMapping(value = "/admin")
//	public ResponseEntity<UserDto> createAdmin(@RequestBody UserDto userDto) {
//		userDto = userService.createAdmin(userDto);
//		return new ResponseEntity<UserDto>(userDto, HttpStatus.CREATED);
//	}
//
//	@PostMapping(value = "/user")
//	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
//		userDto = userService.createUser(userDto);
//		return new ResponseEntity<UserDto>(userDto, HttpStatus.CREATED);
//	}

	@PostMapping(value = "/login")
	public ResponseEntity<String> login(@RequestBody UserDto userDto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtTokenProvider.generateToken(userDto);
		return new ResponseEntity<String>(jwt, HttpStatus.OK);
	}

	@PostMapping(value = "/logout")
	public void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

}
