package com.apex.picloud.controllers;

import com.apex.picloud.dtos.RoleDTO;
import com.apex.picloud.models.Role;
import com.apex.picloud.models.User;
import com.apex.picloud.services.IRoleService;
import com.apex.picloud.services.RoleService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/createRole")
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO) {
        RoleDTO createdRole = roleService.createRole(roleDTO);
        return ResponseEntity.ok(createdRole);
    }
    @DeleteMapping("/deleteRole/{idRole}")
    public void DeleteRole(@PathVariable Long idRole) {
         roleService.DeleteRole(idRole);
 }
    @PostMapping("/updateRole/{id}")
    public RoleDTO updateRole(@PathVariable Long id,RoleDTO role){
            return roleService.updateRole(id,role);
    }
    @GetMapping("list-all-roles")
    public List<RoleDTO> showAllRoles(){
        return roleService.showAll();
    }
    @PostMapping("/affect-role-to-user/{userId}/{roleId}")
    public void affectRoleToUser(@PathVariable Long userId , @PathVariable Long roleId){
        roleService.affectRoleToUser(roleId,userId);
    }
    @GetMapping("/getallRoles")
    public List<Role> getallRoles(){
        return roleService.getallRoles();
    }

    @PostConstruct
    public void initializeRoles() {
        if (!roleService.roleExists("USER")) {
            RoleDTO userRole = new RoleDTO();
            userRole.setName("USER");
            roleService.createRole(userRole);
        }
        if (!roleService.roleExists("ADMIN")) {
            RoleDTO adminRole = new RoleDTO();
            adminRole.setName("ADMIN");
            roleService.createRole(adminRole);
        }
    }

}
