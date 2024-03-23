package com.api.service;

import com.api.entities.Role;
import com.api.mapper.RoleMapper;
import com.api.model.RoleInput;
import com.api.output.RoleJSON;
import com.api.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public RoleJSON addRole(RoleInput input) {
        Role role = RoleMapper.inputToRole(input);
        Role savedRole = roleRepository.save(role);
        return RoleMapper.roleToJson(savedRole);
    }

    @Transactional
    public List<RoleJSON> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(RoleMapper::roleToJson)
                .collect(Collectors.toList());
    }

}
