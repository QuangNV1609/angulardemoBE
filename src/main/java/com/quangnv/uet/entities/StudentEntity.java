package com.quangnv.uet.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
public class StudentEntity {
	@Id
	private String id;

	@Column(name = "firstname", length = 15)
	@Type(type = "org.hibernate.type.StringNVarcharType")
	private String firstName;

	@Column(name = "lastname", length = 15)
	@Type(type = "org.hibernate.type.StringNVarcharType")
	private String lastName;

	@Column(name = "dateOfBirth")
	@Temporal(TemporalType.DATE)
	private Date birthDay;

	@Column(name = "gender")
	private Boolean gender;

	@Column(name = "address")
	@Type(type = "org.hibernate.type.StringNVarcharType")
	private String address;

	@Column(name = "phonenumber", length = 10)
	private String phoneNumber;

	@Column(name = "email")
	private String email;

	@CreatedBy
	@JoinColumn(name = "create_by", updatable = false)
	@ManyToOne
	private UserEntity createBy;

	@LastModifiedBy
	@JoinColumn(name = "last_modified_by")
	@ManyToOne
	private UserEntity lastModifiedBy;

	@Column(name = "create_at", updatable = false)
	@CreatedDate
	@Temporal(TemporalType.DATE)
	private Date createAt;

	@Column(name = "last_modified_at")
	@LastModifiedDate
	@Temporal(TemporalType.DATE)
	private Date lastModifiedAt;

}
