package com.apex.picloud.services;

import com.apex.picloud.dtos.RoleDTO;
import com.apex.picloud.models.Role;

import java.util.List;

public interface IRoleService {
    RoleDTO createRole(RoleDTO roleDTO);
    RoleDTO getRoleById(Long id);
    RoleDTO updateRole(Long id, RoleDTO roleDTO);
    void deleteRole(Long id);
    List<Role> getallRoles();


}
