package com.quangnv.uet.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quangnv.uet.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {

	public Optional<UserEntity> findByUsername(String username);

}
