package com.quangnv.uet.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.quangnv.uet.dto.UserDto;

public interface UserService extends UserDetailsService{
	public UserDto createAdmin(UserDto userDto);
	
	public UserDto createUser(UserDto userDto);
	
	public UserDto getUserByUsername(String username);

}
