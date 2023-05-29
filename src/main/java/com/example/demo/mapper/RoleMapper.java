package com.example.demo.mapper;

import com.example.demo.dto.RoleDto;
import com.example.demo.model.Role;

public class RoleMapper {
	public static RoleDto toDto(Role role) {
		RoleDto roleDto = RoleDto.builder()
				.id(role.getId())
				.name(role.getName())
				.build();
		return roleDto;	
	}
}
