package com.quangnv.uet.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleEntity {
	@Id
	private String roleName;
	
	@ManyToMany(mappedBy = "roles")
	private List<UserEntity> users;

}
