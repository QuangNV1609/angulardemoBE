package com.quangnv.uet.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quangnv.uet.dto.UserDto;
import com.quangnv.uet.entities.RoleEntity;
import com.quangnv.uet.entities.UserEntity;
import com.quangnv.uet.repositories.RoleRepository;
import com.quangnv.uet.repositories.UserRepository;
import com.quangnv.uet.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserService {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> optional = userRepository.findByUsername(username);

		if (!optional.isPresent()) {
			throw new UsernameNotFoundException(username + " not found!");
		}

		UserEntity userEntity = optional.get();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (RoleEntity roleEntity : userEntity.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(roleEntity.getRoleName()));
		}

		return new User(userEntity.getUsername(), userEntity.getPassword(), authorities);
	}

	@Override
	public UserDto createAdmin(UserDto userDto) {
		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
		userEntity.setUserId(UUID.randomUUID().toString());
		RoleEntity roleEntity = RoleEntity.builder().roleName("ROLE_ADMIN").build();
		roleEntity = roleRepository.save(roleEntity);
		List<RoleEntity> roleEntities = new ArrayList<RoleEntity>();
		roleEntities.add(roleEntity);
		userEntity.setRoles(roleEntities);
		userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userEntity = userRepository.save(userEntity);
		return modelMapper.map(userEntity, UserDto.class);
	}

	@Override
	public UserDto createUser(UserDto userDto) {
		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
		userEntity.setUserId(UUID.randomUUID().toString());
		RoleEntity roleEntity = RoleEntity.builder().roleName("ROLE_USER").build();
		roleEntity = roleRepository.save(roleEntity);
		List<RoleEntity> roleEntities = new ArrayList<RoleEntity>();
		roleEntities.add(roleEntity);
		userEntity.setRoles(roleEntities);
		userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userEntity = userRepository.save(userEntity);
		return modelMapper.map(userEntity, UserDto.class);
	}

	@Override
	public UserDto getUserByUsername(String username) {
		UserEntity userEntity = userRepository.findByUsername(username).get();
		userEntity.setPassword("");
		List<String> roles = new ArrayList<String>();
		for (RoleEntity role : userEntity.getRoles()) {
			roles.add(role.getRoleName());
		}
		UserDto userDto = UserDto.builder().username(userEntity.getUsername()).roles(roles).build();
		return userDto;
	}

}
