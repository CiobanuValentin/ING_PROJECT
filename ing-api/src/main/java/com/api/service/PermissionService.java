package com.api.service;

import com.api.entities.Permission;
import com.api.mapper.PermissionMapper;
import com.api.model.PermissionInput;
import com.api.output.PermissionJSON;
import com.api.repository.PermissionRepository;
import com.internationalization.Messages;
import com.util.enums.HTTPCustomStatus;
import com.util.exceptions.ApiException;
import com.util.web.JsonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.api.config.ClockConfig.UTC_CLOCK;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionService {

    private final PermissionRepository permissionRepository;

    @Transactional
    public PermissionJSON addPermission(PermissionInput input) {
        Permission permission = PermissionMapper.inputToPermission(input);
        Permission savedPermission = permissionRepository.save(permission);
        return PermissionMapper.permissionToJson(savedPermission);
    }

    @Transactional
    public List<PermissionJSON> getAllPermissions() {
        return permissionRepository.findAll()
                .stream()
                .map(PermissionMapper::permissionToJson)
                .collect(Collectors.toList());
    }

}
