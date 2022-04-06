package com.quangnv.uet.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDto {
	private String id;
	private String firstName;
	private String lastName;
	private Date birthDay;
	private Boolean gender;
	private String address;
	private String phoneNumber;
	private String email;

}
