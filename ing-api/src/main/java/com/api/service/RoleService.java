package com.api.service;

import com.api.entities.Permission;
import com.api.entities.Role;
import com.api.entities.RolePermission;
import com.api.mapper.RoleMapper;
import com.api.model.AssignationInput;
import com.api.model.RoleInput;
import com.api.output.RoleJSON;
import com.api.repository.PermissionRepository;
import com.api.repository.RolePermissionRepository;
import com.api.repository.RoleRepository;
import com.internationalization.Messages;
import com.util.enums.HTTPCustomStatus;
import com.util.exceptions.ApiException;
import com.util.web.JsonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;

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

    @Transactional
    public JsonResponse assignPermission(AssignationInput input, Locale locale) {
        Role role = roleRepository.findById(input.getSourceKey()).orElseThrow(
                () -> new ApiException(Messages.get("ROLE.NOT.EXIST", locale), HTTPCustomStatus.NOT_FOUND)
        );

        Permission permission = permissionRepository.findById(input.getDestinationKey()).orElseThrow(
                () -> new ApiException(Messages.get("PERMISSION.NOT.EXIST", locale), HTTPCustomStatus.NOT_FOUND)
        );

        RolePermission rolePermission = new RolePermission(role, permission);
        rolePermissionRepository.save(rolePermission);

        return new JsonResponse()
                .with("Status", "ok")
                .with("message", "Permission was successfully assigned!")
                .done();
    }

}
