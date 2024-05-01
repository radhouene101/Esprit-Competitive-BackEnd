package com.apex.picloud.services;

import com.apex.picloud.dtos.RoleDTO;
import com.apex.picloud.models.Role;
import com.apex.picloud.repositories.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        Role savedRole = roleRepository.save(role);
        RoleDTO savedRoleDTO = new RoleDTO();
        savedRoleDTO.setName(savedRole.getName());
        return savedRoleDTO;
    }
}
