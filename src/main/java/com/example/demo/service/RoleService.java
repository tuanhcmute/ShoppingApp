package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.RoleDto;

public interface RoleService {
	List<RoleDto> findAll();
	RoleDto upsert(RoleDto roleDto);
	RoleDto findById(String id);
}
