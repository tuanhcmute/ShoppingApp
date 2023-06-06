package com.example.demo.mapper;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;

public class UserMapper {
	public static UserDto toDto(User user) {
		UserDto userDto = UserDto.builder()
							.id(user.getId())
							.email(user.getEmail())
							.roles(user.getRoles())
							.build();
		return userDto;
	}
}
