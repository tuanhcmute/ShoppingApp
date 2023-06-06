package com.example.demo.dto;

import java.util.Set;

import com.example.demo.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
	private String id;
	private String email;
	private String fullName;
	private Set<Role> roles;
}
