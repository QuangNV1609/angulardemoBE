package com.quangnv.uet.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class RoleEntity {
	@Id
	private String roleName;
	
	@ManyToMany(mappedBy = "roles")
	private List<UserEntity> users;

}
