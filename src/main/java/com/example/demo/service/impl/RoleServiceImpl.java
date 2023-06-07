package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.RoleDto;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.RoleService;
import com.example.demo.util.IdUtil;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public List<RoleDto> findAll() {
		List<Role> roles = roleRepository.findAll();
		List<RoleDto> roleDtos = new ArrayList<>();
		if(roles == null || roles.size() == 0) return null;
		for(Role role : roles) {
			roleDtos.add(RoleMapper.toDto(role));
		}
		return roleDtos;
	}

	@Override
	public RoleDto upsert(RoleDto roleDto) {
		Role role = roleRepository.findById(roleDto.getId())
				.map(c -> {
					c.setName(roleDto.getName());
					return roleRepository.save(c);
				}).orElseGet(() -> {
					Role c = Role.builder()
										.id(IdUtil.generatedId())
										.name(roleDto.getName())
										.build();
					return roleRepository.save(c);
				});
		return RoleMapper.toDto(role);
	}

	@Override
	public RoleDto findById(String id) {
		Optional<Role> role = roleRepository.findById(id);
		if(role.isPresent()) {
			return RoleMapper.toDto(role.get());
		}
		return null;
	}
	
}
