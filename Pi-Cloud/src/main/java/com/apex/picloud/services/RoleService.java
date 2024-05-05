package com.apex.picloud.services;

import com.apex.picloud.dtos.RoleDTO;
import com.apex.picloud.models.Role;
import com.apex.picloud.models.User;
import com.apex.picloud.repositories.RoleRepository;
import com.apex.picloud.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RoleService implements IRoleService{

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        Role savedRole = roleRepository.save(role);
        RoleDTO savedRoleDTO = new RoleDTO();
        savedRoleDTO.setName(savedRole.getName());
        return savedRoleDTO;
    }

    @Override
    public RoleDTO getRoleById(Long id) {
        return null;
    }

    public void  DeleteRole(Long id){
        roleRepository.deleteById(id);

    }
    public RoleDTO updateRole(Long id,RoleDTO roleDTO){
        Role role = roleRepository.findById(id).get();
        role.setName(roleDTO.getName());
        roleRepository.save(role);
        return roleDTO;

    }
    public boolean roleExists(String roleName) {
        List<Role> allRoles = roleRepository.findAll();
        for (Role role : allRoles) {
            if (role.getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void deleteRole(Long id) {

    }

    @Override
    public List<Role> getallRoles() {
        return roleRepository.findAll();
    }

    public List<RoleDTO> showAll() {
        List<Role> roleList =roleRepository.findAll();
        List<RoleDTO> roleDtoList =new ArrayList<>();
        RoleDTO roleDto =new RoleDTO();
        roleList.forEach(role -> {
                roleDto.setId(role.getId());
                roleDto.setName(role.getName());
                roleDtoList.add(roleDto);

                }
        );
        return roleDtoList;
    }
    public void affectRoleToUser(Long roleId , Long userid){
        User user = userRepository
                .findById(userid)
                .orElseThrow(()-> new EntityNotFoundException("user provided doesnt exist :) c radhouene"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(()-> new EntityNotFoundException("this role doesnt exist!"));
        user.getRoles().add(role);
        userRepository.save(user);
    }


}
